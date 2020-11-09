# Kotlin Utilities

## Running
```$xslt
java -jar parse.jar /Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/src/main/resources/file1.csv
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
