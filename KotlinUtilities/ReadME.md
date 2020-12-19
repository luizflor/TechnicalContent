# Kotlin Utilities

## Running
```$xslt
java -jar parse.jar /Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/src/main/resources/file1.csv
java -jar parse.jar ./src/main/resources/config.properties
```

## TODO
1. Build class descriptor<br/>
this is to convert a descriptor file like 
```$xslt
id, int
fn, string
ln, string
```
into
```$xslt
data class Person(val id: Int, val firstName: String, val lastName: String){
    override fun toString(): String {
        return "Person(id=$id, firstName='$firstName', lastName='$lastName')"
    }
}
```
 
2. Generate fake data api and csv file


# [Faker](https://www.npmjs.com/package/faker)
Generate fake data
## API Methods
* address
  * zipCode
  * zipCodeByState
  * city
  * cityPrefix
  * citySuffix
  * streetName
  * streetAddress
  * streetSuffix
  * streetPrefix
  * secondaryAddress
  * county
  * country
  * countryCode
  * state
  * stateAbbr
  * latitude
  * longitude
  * direction
  * cardinalDirection
  * ordinalDirection
  * nearbyGPSCoordinate
  * timeZone
* commerce
  * color
  * department
  * productName
  * price
  * productAdjective
  * productMaterial
  * product
  * productDescription
* company
  * suffixes
  * companyName
  * companySuffix
  * catchPhrase
  * bs
  * catchPhraseAdjective
  * catchPhraseDescriptor
  * catchPhraseNoun
  * bsAdjective
  * bsBuzz
  * bsNoun
* database
  * column
  * type
  * collation
  * engine
* date
  * past
  * future
  * between
  * recent
  * soon
  * month
  * weekday
* fake
* finance
  * account
  * accountName
  * routingNumber
  * mask
  * amount
  * transactionType
  * currencyCode
  * currencyName
  * currencySymbol
  * bitcoinAddress
  * litecoinAddress
  * creditCardNumber
  * creditCardCVV
  * ethereumAddress
  * iban
  * bic
  * transactionDescription
* git
  * branch
  * commitEntry
  * commitMessage
  * commitSha
  * shortSha  
* hacker
  * abbreviation
  * adjective
  * noun
  * verb
  * ingverb
  * phrase
* helpers
  * randomize
  * slugify
  * replaceSymbolWithNumber
  * replaceSymbols
  * replaceCreditCardSymbols
  * repeatString
  * regexpStyleStringParse
  * shuffle
  * mustache
  * createCard
  * contextualCard
  * userCard
  * createTransaction
* image
  * image
  * avatar
  * imageUrl
  * abstract
  * animals
  * business
  * cats
  * city
  * food
  * nightlife
  * fashion
  * people
  * nature
  * sports
  * technics
  * transport
  * dataUri
  * lorempixel
  * unsplash
  * lorempicsum
* internet
  * avatar
  * email
  * exampleEmail
  * userName
  * protocol
  * url
  * domainName
  * domainSuffix
  * domainWord
  * ip
  * ipv6
  * userAgent
  * color
  * mac
  * password
* lorem
  * word
  * words
  * sentence
  * slug
  * sentences
  * paragraph
  * paragraphs
  * text
  * lines
* name
  * firstName
  * lastName
  * findName
  * jobTitle
  * gender
  * prefix
  * suffix
  * title
  * jobDescriptor
  * jobArea
  * jobType
* phone
  * phoneNumber
  * phoneNumberFormat
  * phoneFormats
* random
  * number
  * float
  * arrayElement
  * arrayElements
  * objectElement
  * uuid
  * boolean
  * word
  * words
  * image
  * locale
  * alpha
  * alphaNumeric
  * hexaDecimal
* system
  * fileName
  * commonFileName
  * mimeType
  * commonFileType
  * commonFileExt
  * fileType
  * fileExt
  * directoryPath
  * filePath
  * semver
* time
* recent    
* unique
  * vehicle
  * vehicle
  * manufacturer
  * model
  * type
  * fuel
  * vin
  * color

# [JSON-SERVER](https://www.npmjs.com/package/json-server)

## Plural routes
```
GET    /posts
GET    /posts/1
POST   /posts
PUT    /posts/1
PATCH  /posts/1
DELETE /posts/1
```
## Singular routes
```
GET    /profile
POST   /profile
PUT    /profile
PATCH  /profile
Filter
```
## Use . to access deep properties
```
GET /posts?title=json-server&author=typicode
GET /posts?id=1&id=2
GET /comments?author.name=typicode
```
## Paginate

Use _page and optionally _limit to paginate returned data.

In the Link header you'll get first, prev, next and last links.
```
GET /posts?_page=7
GET /posts?_page=7&_limit=20
10 items are returned by default
```
## Sort
Add _sort and _order (ascending order by default)
```
GET /posts?_sort=views&_order=asc
GET /posts/1/comments?_sort=votes&_order=asc
For multiple fields, use the following format:

GET /posts?_sort=user,views&_order=desc,asc
```
## Slice
Add _start and _end or _limit (an X-Total-Count header is included in the response)
```
GET /posts?_start=20&_end=30
GET /posts/1/comments?_start=20&_end=30
GET /posts/1/comments?_start=20&_limit=10
Works exactly as Array.slice (i.e. _start is inclusive and _end exclusive)
```