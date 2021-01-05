import com.techware.generate.Descriptor
import com.techware.generate.Type
import com.techware.springapi.GenerateSpringApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerateSpringApiTest {
    fun getTypeList(): List<Type> {
        val descriptorPerson2Str="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n"

        val typeList = Descriptor.Companion.convertDescriptorToTypeList(descriptorPerson2Str)
        return typeList
    }
    fun getTypeList2(): List<Type> {
        val descriptorPerson2Str="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n" +
                "birthdate, date\n" +
                "ismodified, boolean\n" +
                "timestamp, instant\n" +
                "salary, double\n"
        val typeList = Descriptor.Companion.convertDescriptorToTypeList(descriptorPerson2Str)
        return typeList
    }

    @Test
    fun generateSpringTest() {
        GenerateSpringApi.copyFiles(PATH_DESTINATION_SPRING_API)
    }

    @Test
    fun generateApiDataControllerTest() {
        val iDataController = GenerateSpringApi.generateApiDataController("customer")
//        println(iDataController)
        val expected =
            "package com.demospring.customer.api\n" +
                    "\n" +
                    "import com.demospring.customer.model.Customer\n" +
                    "import org.slf4j.Logger\n" +
                    "import org.springframework.http.ResponseEntity\n" +
                    "import org.springframework.web.bind.annotation.*\n" +
                    "import org.springframework.web.multipart.MultipartFile\n" +
                    "\n" +
                    "interface IDataController {\n" +
                    "    @GetMapping(\"/customer\", produces = [\"application/json\"])\n" +
                    "    fun findAll(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) _page: Int?, @RequestParam(required = false) _limit: Int?): ResponseEntity<MutableIterable<Customer>>\n" +
                    "\n" +
                    "    @GetMapping(\"/customer/count\", produces = [\"application/json\"])\n" +
                    "    fun count(@RequestHeader(required = false) activityId: String?): ResponseEntity<Long>\n" +
                    "\n" +
                    "    @GetMapping(\"/customer/search\", produces = [\"application/json\"])\n" +
                    "    fun search(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) q: String): ResponseEntity<Iterable<Customer>>\n" +
                    "\n" +
                    "    @GetMapping(\"/customer/{id}\", produces = [\"application/json\"])\n" +
                    "    fun findById(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any>\n" +
                    "\n" +
                    "    @PostMapping(\"/customer\", produces = [\"application/json\"])\n" +
                    "    fun insert(@RequestHeader(required = false) activityId: String?, @RequestBody customerReq: Customer): ResponseEntity<Any>\n" +
                    "\n" +
                    "    @DeleteMapping(\"/customer/{id}\", produces = [\"application/json\"])\n" +
                    "    fun delete(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any>\n" +
                    "\n" +
                    "    @PutMapping(\"/customer/{id}\", produces = [\"application/json\"])\n" +
                    "    fun update(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long, @RequestBody customerReq: Customer): ResponseEntity<Any>\n" +
                    "\n" +
                    "    @GetMapping(\"/customer/download\")\n" +
                    "    fun download(@RequestParam name: String): ResponseEntity<Any>\n" +
                    "\n" +
                    "    @PostMapping(\"/customer/upload\")\n" +
                    "    fun upload(@RequestParam file: MultipartFile): ResponseEntity<Any>\n" +
                    "}"
        assertEquals(expected,iDataController)
    }

    @Test
    fun generateControllerTest() {
        val controller = GenerateSpringApi.generateController("customer")
        println(controller)

        val expected =
            "package com.demospring.customer.api\n" +
                    "\n" +
                    "import com.demospring.controller.BaseController\n" +
                    "import com.demospring.customer.model.Customer\n" +
                    "import com.demospring.customer.service.CustomerService\n" +
                    "import org.apache.logging.log4j.ThreadContext\n" +
                    "import org.slf4j.LoggerFactory\n" +
                    "import org.springframework.http.HttpHeaders\n" +
                    "import org.springframework.http.ResponseEntity\n" +
                    "import org.springframework.web.bind.annotation.*\n" +
                    "import org.springframework.web.multipart.MultipartFile\n" +
                    "\n" +
                    "@RestController\n" +
                    "@RequestMapping(\"/api/customer\")\n" +
                    "class CustomerController(private val service: CustomerService): BaseController(), IDataController {\n" +
                    "\tcompanion object {\n" +
                    "\t\tprivate val logger = LoggerFactory.getLogger(CustomerController::class.java)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@GetMapping(\"/customer\", produces = [\"application/json\"])\n" +
                    "\toverride fun findAll(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) _page: Int?, @RequestParam(required = false) _limit: Int?): ResponseEntity<MutableIterable<Customer>> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"findAll started\")\n" +
                    "\n" +
                    "\t\tval customer = service.findAll(_page, _limit)\n" +
                    "\n" +
                    "\t\tlogger.info(\"findAll ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(customer)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@GetMapping(\"/customer/count\", produces = [\"application/json\"])\n" +
                    "\toverride fun count(@RequestHeader(required = false) activityId: String?): ResponseEntity<Long> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"count started\")\n" +
                    "\t\tval count = service.count()\n" +
                    "\t\tlogger.info(\"count ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(count)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@GetMapping(\"/customer/search\", produces = [\"application/json\"])\n" +
                    "\toverride fun search(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) q: String): ResponseEntity<Iterable<Customer>> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"search started\")\n" +
                    "\t\tval customer = (service.findByQ(q))\n" +
                    "\t\tlogger.info(\"search ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(customer)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@GetMapping(\"/customer/{id}\", produces = [\"application/json\"])\n" +
                    "\toverride fun findById(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"findById started\")\n" +
                    "\n" +
                    "\t\tval customer = service.findById(id)\n" +
                    "\t\tlogger.info(\"findById ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(customer)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@PostMapping(\"/customer\", produces = [\"application/json\"])\n" +
                    "\toverride fun insert(@RequestHeader(required = false) activityId: String?, @RequestBody customerReq: Customer): ResponseEntity<Any> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"insert started\")\n" +
                    "\n" +
                    "\t\tval newCustomer = service.insert(customerReq)\n" +
                    "\t\tlogger.info(\"insert ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(newCustomer)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@DeleteMapping(\"/customer/{id}\", produces = [\"application/json\"])\n" +
                    "\toverride fun delete(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"delete started\")\n" +
                    "\n" +
                    "\t\tservice.deleteById(id)\n" +
                    "\t\tlogger.info(\"delete ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(\"OK\")\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@PutMapping(\"/customer/{id}\", produces = [\"application/json\"])\n" +
                    "\toverride fun update(@RequestHeader(required = false)  activityId: String?, @PathVariable id: Long, @RequestBody customerReq: Customer): ResponseEntity<Any> {\n" +
                    "\t\tif (activityId != null) {\n" +
                    "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"update started\")\n" +
                    "\n" +
                    "\t\tval newCustomer = service.update(id=id, customer = customerReq)\n" +
                    "\t\tlogger.info(\"update ended\")\n" +
                    "\t\treturn ResponseEntity.ok().body(newCustomer)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@GetMapping(\"/customer/download\")\n" +
                    "\toverride fun download(@RequestParam name: String):ResponseEntity<Any>{\n" +
                    "\t\tif(name.isEmpty()) {\n" +
                    "\t\t\tthrow IllegalArgumentException(\"name is required\")\n" +
                    "\t\t}\n" +
                    "\t\tval data = service.getCSV()\n" +
                    "\n" +
                    "\t\treturn ResponseEntity\n" +
                    "\t\t\t\t.ok()\n" +
                    "\t\t\t\t.header(HttpHeaders.CONTENT_DISPOSITION,\"attachment; fileName=\\\"\$name\\\"\")\n" +
                    "\t\t\t\t.body(data)\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@PostMapping(\"/customer/upload\")\n" +
                    "\toverride fun upload(@RequestParam file: MultipartFile): ResponseEntity<Any> {\n" +
                    "\t\tval fileName = file.originalFilename\n" +
                    "\t\tif(fileName.isNullOrEmpty()) {\n" +
                    "\t\t\tthrow IllegalArgumentException(\"File name is required\")\n" +
                    "\t\t}\n" +
                    "\t\tlogger.info(\"upload: \$fileName\")\n" +
                    "\t\tval data = service.saveCSV(file.inputStream)\n" +
                    "\n" +
                    "\t\treturn ResponseEntity.ok().body(data)\n" +
                    "\t}\n" +
                    "}\n"
        assertEquals(expected,controller)
    }

    @Test
    fun generateBootstrap() {
        val bootstrap = GenerateSpringApi.generateBootstrap("customer")
//        println(bootstrap)

        val expected =
            "package com.demospring.customer.bootstrap\n" +
                    "\n" +
                    "import com.demospring.customer.service.CustomerService\n" +
                    "import org.slf4j.LoggerFactory\n" +
                    "import org.springframework.context.ApplicationListener\n" +
                    "import org.springframework.context.annotation.Profile\n" +
                    "import org.springframework.context.event.ContextRefreshedEvent\n" +
                    "import org.springframework.stereotype.Component\n" +
                    "\n" +
                    "@Component\n" +
                    "@Profile(\"default\")\n" +
                    "class CustomerBootstrap(val customerService: CustomerService) : ApplicationListener<ContextRefreshedEvent> {\n" +
                    "    companion object {\n" +
                    "        private val logger = LoggerFactory.getLogger(CustomerBootstrap::class.java)\n" +
                    "    }\n" +
                    "    override fun onApplicationEvent(event: ContextRefreshedEvent) {\n" +
                    "       // fetch all customer\n" +
                    "        logger.info(\"Customers found with findAll():\")\n" +
                    "        logger.info(\"-------------------------------\")\n" +
                    "        customerService.findAll().forEach { logger.info(it.toString()) }\n" +
                    "        logger.info(\"\")\n" +
                    "    }\n" +
                    "}"

        assertEquals(expected, bootstrap)

    }

    @Test
    fun generateModelTest() {
        val typeList = getTypeList()
        val model = GenerateSpringApi.generateModel("customer", typeList)
        println(model)

        val expected =
            "package com.demospring.customer.model\n" +
                    "import java.time.Instant\n" +
                    "import java.util.*\n" +
                    "import javax.persistence.*\n" +
                    "import java.time.LocalDate\n" +
                    "\n" +
                    "@Entity\n" +
                    "data class Customer(\n" +
                    "\n" +
                    "\t\t@Id @GeneratedValue(strategy = GenerationType.IDENTITY)\n" +
                    "\t\t@Column(name=\"id\", unique = true, nullable = false)\n" +
                    "\t\tval id: Long = -1,\n" +
                    "\n" +
                    "\t\t@Column(name=\"firstname\")\n" +
                    "\t\tval firstname: String,\n" +
                    "\n" +
                    "\t\t@Column(name=\"lastname\")\n" +
                    "\t\tval lastname: String)\n"
        assertEquals(expected, model)
    }

    @Test
    fun generateRepositoryTest() {
        val repository = GenerateSpringApi.generateRepository("person")
        println(repository)

        val expected =
            "package com.demospring.person.service\n" +
                    "\n" +
                    "import com.demospring.person.model.Person\n" +
                    "import org.springframework.data.repository.CrudRepository\n" +
                    "import org.springframework.data.repository.PagingAndSortingRepository\n" +
                    "\n" +
                    "interface PersonRepository : CrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {\n" +
                    "\n" +
                    "}\n"
        assertEquals(expected,repository)
    }

    @Test
    fun generateDataServiceTest() {
        val typeList = getTypeList2()
        val dataService = GenerateSpringApi.generateDataService("customer", typeList)
        println(dataService)

        val expected =
            "package com.demospring.customer.service\n" +
                    "\n" +
                    "import com.demospring.customer.model.Customer\n" +
                    "import com.demospring.exceptions.NotFoundException\n" +
                    "import org.springframework.data.domain.PageRequest\n" +
                    "import org.springframework.stereotype.Service\n" +
                    "import java.io.InputStream\n" +
                    "import java.lang.StringBuilder\n" +
                    "import java.nio.charset.StandardCharsets\n" +
                    "import java.util.*\n" +
                    "import java.time.Instant\n" +
                    "import java.time.LocalDate\n" +
                    "import java.time.ZoneId\n" +
                    "import java.time.ZoneOffset\n" +
                    "import java.time.format.DateTimeFormatter\n" +
                    "@Service\n" +
                    "class CustomerService(private val repository: CustomerRepository) : IDataService {\n" +
                    "    override fun findByQ(q: String): Iterable<Customer> {\n" +
                    "        TODO(\"Not implemented\")\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun findAll(): MutableIterable<Customer> {\n" +
                    "        return repository.findAll()\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun findAll(page:Int?, size:Int?): MutableIterable<Customer> {\n" +
                    "        if(page == null || size == null) {\n" +
                    "            return findAll()\n" +
                    "        }\n" +
                    "        val pages = PageRequest.of(page,size)\n" +
                    "        return repository.findAll(pages).content\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun findById(id: Long): Optional<Customer> {\n" +
                    "        val customer = repository.findById(id)\n" +
                    "        if(!customer.isPresent) {\n" +
                    "            throw NotFoundException(\"Customer id: \$id not found\")\n" +
                    "        }\n" +
                    "        return customer\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun deleteById(id: Long) {\n" +
                    "        validateId(id)\n" +
                    "\n" +
                    "        repository.deleteById(id)\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun count(): Long {\n" +
                    "        return repository.count()\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun update(id: Long, customer: Customer): Customer? {\n" +
                    "        if(id != customer.id) {\n" +
                    "            throw IllegalArgumentException(\"update with id: \$id is not the same as \${customer.id}\")\n" +
                    "        }\n" +
                    "        validateId(id)\n" +
                    "\n" +
                    "        return repository.save(customer)\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun insert(customer: Customer) : Customer?  {\n" +
                    "        if(repository.existsById(customer.id)) {\n" +
                    "            throw IllegalArgumentException(\"Customer id: \${customer.id} already in database\")\n" +
                    "        }\n" +
                    "\n" +
                    "        return repository.save(customer)\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun getCSV(): String {\n" +
                    "        val header = \"id, firstname, lastname, birthdate, ismodified, timestamp, salary\"\n" +
                    "        val all = findAll()\n" +
                    "        val sb = StringBuilder()\n" +
                    "        sb.appendln(header)\n" +
                    "        all.forEach {\n" +
                    "            sb.appendln(\"\${it.id}, \${it.firstname}, \${it.lastname}, \${it.birthdate}, \${it.ismodified}, \${it.timestamp}, \${it.salary}\")\n" +
                    "        }\n" +
                    "        return sb.toString()\n" +
                    "    }\n" +
                    "\n" +
                    "    override fun saveCSV(fileInput: InputStream): MutableList<Customer?> {\n" +
                    "        val fileData = String(fileInput.readBytes(), StandardCharsets.UTF_8)\n" +
                    "        val dataList = fileData.split(System.lineSeparator())\n" +
                    "        val result = mutableListOf<Customer?>()\n" +
                    "        dataList.forEachIndexed { index, s ->\n" +
                    "            if (index == 0) {\n" +
                    "                return@forEachIndexed\n" +
                    "            }\n" +
                    "            if (s.isNullOrEmpty()) {\n" +
                    "                return@forEachIndexed\n" +
                    "            }\n" +
                    "            val parsed = parseLine(s)\n" +
                    "\n" +
                    "            val newCustomer = Customer(\n" +
                    "\t\t\tid = -1, \n" +
                    "\t\t\tfirstname = parsed[1], \n" +
                    "\t\t\tlastname = parsed[2], \n" +
                    "\t\t\tbirthdate = convertStringToObject(\"java.time.LocalDate\",parsed[3]) as LocalDate, \n" +
                    "\t\t\tismodified = convertStringToObject(\"kotlin.Boolean\",parsed[4]) as Boolean, \n" +
                    "\t\t\ttimestamp = convertStringToObject(\"java.time.Instant\",parsed[5]) as Instant, \n" +
                    "\t\t\tsalary = convertStringToObject(\"kotlin.Double\",parsed[6]) as Double)\n" +
                    "\n" +
                    "            val customer = insert(newCustomer)\n" +
                    "            result.add(customer)\n" +
                    "        }\n" +
                    "        return result\n" +
                    "    }\n" +
                    "\n" +
                    "    /**\n" +
                    "     * Convert string to object\n" +
                    "     */\n" +
                    "    fun convertStringToObject(\n" +
                    "            type: String,\n" +
                    "            value: String\n" +
                    "    ): Any {\n" +
                    "        return when (type) {\n" +
                    "            \"kotlin.Int\" ->  value.toInt()\n" +
                    "            \"kotlin.Short\" ->  value.toShort()\n" +
                    "            \"kotlin.Byte\" ->  value.toByte()\n" +
                    "            \"kotlin.Boolean\" ->  value.toBoolean()\n" +
                    "            \"kotlin.Float\" ->  value.toFloat()\n" +
                    "            \"kotlin.Double\" ->  value.toDouble()\n" +
                    "            \"java.time.Instant\" ->  value.fromDateTimeToInstant()\n" +
                    "            \"java.time.LocalDate\" ->  value.fromDateToInstant().toDate()\n" +
                    "            \"kotlin.String\" ->  value\n" +
                    "            else -> value\n" +
                    "        }\n" +
                    "    }\n" +
                    "//    val formatter = DateTimeFormatter.ofPattern(\"M/d/yyyy\")\n" +
                    "    val formatter = DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")\n" +
                    "// parse string to instant\n" +
                    "\n" +
                    "    fun String.fromDateToInstant(): Instant {\n" +
                    "        val localDate = LocalDate.parse(this.trim(), formatter)\n" +
                    "        return localDate.atStartOfDay(ZoneId.of(ZoneOffset.UTC.id)).toInstant()!!\n" +
                    "    }\n" +
                    "\n" +
                    "    fun String.fromDateTimeToInstant(): Instant {\n" +
                    "        val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME\n" +
                    "//        .withLocale(Locale.US)\n" +
                    "                .withZone(ZoneOffset.UTC)\n" +
                    "        val localDate = LocalDate.parse(this.trim(), formatter)\n" +
                    "        return localDate.atStartOfDay(ZoneId.of(ZoneOffset.UTC.id)).toInstant()!!\n" +
                    "    }\n" +
                    "    // convert instant to date\n" +
                    "    fun Instant.toDate(): LocalDate {\n" +
                    "        return this.atOffset(ZoneOffset.UTC).toLocalDate()\n" +
                    "    }    private fun parseLine(line:String):List<String>{\n" +
                    "//        val pattern = \"[^A-Za-z0-9 _]\".toRegex()\n" +
                    "        var list =line.split(',').toMutableList()\n" +
                    "//        for(i in list.indices){\n" +
                    "//            list[i] = list[i].replace(pattern,\"\")\n" +
                    "//        }\n" +
                    "        return list\n" +
                    "    }\n" +
                    "\n" +
                    "    private fun validateId(id: Long) {\n" +
                    "        if(!repository.existsById(id)) {\n" +
                    "            throw NotFoundException(\"Customer id: \$id not found\")\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n"
        assertEquals(expected, dataService)
    }

    @Test
    fun generateIDataServiceTest() {
        val iDataService = GenerateSpringApi.generateIDataService("customer")
        println(iDataService)

        val expected =
            "package com.demospring.customer.service\n" +
                    "\n" +
                    "import com.demospring.customer.model.Customer\n" +
                    "import java.io.InputStream\n" +
                    "import java.util.*\n" +
                    "\n" +
                    "interface IDataService {\n" +
                    "    fun findByQ(q: String): Iterable<Customer>\n" +
                    "    fun findAll(): MutableIterable<Customer>\n" +
                    "    fun findAll(page: Int?, size: Int?): MutableIterable<Customer>\n" +
                    "    fun findById(id: Long): Optional<Customer>\n" +
                    "    fun deleteById(id: Long)\n" +
                    "    fun count(): Long\n" +
                    "    fun update(id: Long, customer: Customer): Customer?\n" +
                    "    fun insert(customer: Customer) : Customer?\n" +
                    "    fun getCSV(): String\n" +
                    "    fun saveCSV(fileInput: InputStream): MutableList<Customer?>\n" +
                    "}"
        assertEquals(expected,iDataService)
    }

    @Test
    fun generateSpringPackageTest() {
        val typeList = getTypeList()
        GenerateSpringApi.generateSpringPackage(className = "customer", targetFolder = PATH_DESTINATION_SPRING_API, typeList = typeList)

    }

    @Test
    fun generateSpringFilesTest() {
        GenerateSpringApi.generateSpringFiles(targetDescriptor = DESCRIPTOR_FILES,targetFolder = PATH_DESTINATION_SPRING_API)
    }

    companion object {
        const val PATH_DESTINATION_SPRING_API =
            "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/springapi"
//        const val PATH_PACKAGE_SPRING_API = "/src/main/kotlin/com/demospring"

        const val FILE_NAME_PERSON1 = "./src/test/resources/Descriptors/person1.txt"
        const val FILE_NAME_PERSON2 = "./src/test/resources/Descriptors/person2.txt"
        const val DESCRIPTOR_FILES = "./src/test/resources/Descriptors"
    }
}