# Setup cert on MacOS
# 1. Locate where your certificate file is. It is likely to be somewhere near your web server configurations.
# 2. Open up Keychain Access. You can get to it from Application/Utilities/Keychain Access.app.
# 3. Drag your certificate into Keychain Access.
# 4. Go into the Certificates section and locate the certificate you just added
# Double click on it, enter the trust section and under “When using this certificate” select “Always Trust”

# set variable trustpass
pass=trustpass
computerName=$(hostname -fs 2>&1)
stars="\n******************************************************\n*"

# Generate the self-signed certificate and place it in the KeyStore.
rm -f root.jks
rm -f root-ca-trust.cer 
rm -f root-ca.csr 
rm -f root-ca.cer
rm -f leaf.csr
rm -f leaf.cer

# This must be in one line
echo "$stars 1. creates root.jks: key pair and self-signed certificate $stars"
keytool -genkeypair -dname "CN=root, OU=root, O=WFC, L=San Francisco, C=US" -alias root-ca  -keyalg RSA  -validity 3650  -keysize 2048  -keystore ./root.jks   -storepass $pass  -keypass $pass  -storetype PKCS12 -ext BC=ca:true -ext KU=digitalSignature,keyCertSign,cRLSign  -ext EKU=serverAuth,clientAuth, -v

# Display key store
keytool -list -v -keystore ./root.jks -storepass $pass

# creates root.cer to be used to creates truststore
echo "$stars 2. creates root.cer to be used to creates truststore $stars"
keytool -exportcert  -keystore root.jks -alias root-ca -file root-ca-trust.cer  -storepass $pass  -storetype PKCS12  -v

keytool -printcert -file root-ca-trust.cer -v

echo "$stars 3. generate cert request for ca signing $stars"
keytool -certreq -keystore root.jks -storepass $pass -alias root-ca -file root-ca.csr -ext BC=ca:true -ext KU=digitalSignature,keyCertSign,cRLSign  -ext EKU=serverAuth,clientAuth, -v

echo "$stars 4. generate signed cert  $stars"
keytool -gencert -keystore root.jks -storepass $pass -alias root-ca -infile root-ca.csr -outfile root-ca.cer -validity 3650 -ext bc:critical=ca:true
echo CA created. Import ca.cer in windows and firefox certificate store as "Trusted CA".

keytool -printcert -file root-ca.cer -v

echo "$stars 5. create server cert key for $computerName and store in jks $stars"
keytool -genkeypair -alias leaf -keystore root.jks -keyalg RSA -validity 3650 -ext bc=ca:false -ext san=dns:$computerName,dns:localhost,ip:127.0.0.1 -dname "CN=Leaf" -storepass $pass -keypass $pass 

echo "$stars 6. generate cert request $stars"
keytool -certreq -keystore root.jks -storepass $pass -alias leaf -file leaf.csr -ext bc=ca:false -ext san=dns:$computerName,dns:localhost,ip:127.0.0.1

echo "$stars 7. generate signed cert $stars" 
keytool -gencert -keystore root.jks -storepass $pass -alias root-ca -infile leaf.csr -outfile leaf.cer -validity 3650 -ext bc=ca:false -ext san=dns:$computerName,dns:localhost,ip:127.0.0.1

keytool -printcert -file leaf.cer -v

echo "$stars 8. install in orig keystore $stars" 
keytool -importcert -keystore root.jks -storepass $pass -file leaf.cer -alias leaf

echo "$stars 9. content of root.jks: $stars" 
keytool -list -v -storepass $pass -keystore root.jks