package com.techware.springapi

import com.techware.generate.Descriptor
import com.techware.generate.Type
import com.techware.utilities.JarFiles
import java.io.File
import java.lang.StringBuilder

class GenerateSpringApi {
    companion object {

        /**
         * copy a react template into the target folder
         */
        fun copyFiles(targetFolder: String) {
            JarFiles.copyJarFiles(SPRING_FILES, targetFolder)
        }

        fun generateApiDataController(className: String): String {
            val iDataController =
                "package com.demospring.${className.toLowerCase()}.api\n" +
                        "\n" +
                        "import com.demospring.${className.toLowerCase()}.model.${className.capitalize()}\n" +
                        "import org.slf4j.Logger\n" +
                        "import org.springframework.http.ResponseEntity\n" +
                        "import org.springframework.web.bind.annotation.*\n" +
                        "import org.springframework.web.multipart.MultipartFile\n" +
                        "\n" +
                        "interface IDataController {\n" +
                        "    @GetMapping(\"/${className.toLowerCase()}\", produces = [\"application/json\"])\n" +
                        "    fun findAll(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) _page: Int?, @RequestParam(required = false) _limit: Int?): ResponseEntity<MutableIterable<${className.capitalize()}>>\n" +
                        "\n" +
                        "    @GetMapping(\"/${className.toLowerCase()}/count\", produces = [\"application/json\"])\n" +
                        "    fun count(@RequestHeader(required = false) activityId: String?): ResponseEntity<Long>\n" +
                        "\n" +
                        "    @GetMapping(\"/${className.toLowerCase()}/search\", produces = [\"application/json\"])\n" +
                        "    fun search(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) q: String): ResponseEntity<Iterable<${className.capitalize()}>>\n" +
                        "\n" +
                        "    @GetMapping(\"/${className.toLowerCase()}/{id}\", produces = [\"application/json\"])\n" +
                        "    fun findById(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any>\n" +
                        "\n" +
                        "    @PostMapping(\"/${className.toLowerCase()}\", produces = [\"application/json\"])\n" +
                        "    fun insert(@RequestHeader(required = false) activityId: String?, @RequestBody ${className.toLowerCase()}Req: ${className.capitalize()}): ResponseEntity<Any>\n" +
                        "\n" +
                        "    @DeleteMapping(\"/${className.toLowerCase()}/{id}\", produces = [\"application/json\"])\n" +
                        "    fun delete(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any>\n" +
                        "\n" +
                        "    @PutMapping(\"/${className.toLowerCase()}/{id}\", produces = [\"application/json\"])\n" +
                        "    fun update(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long, @RequestBody ${className.toLowerCase()}Req: ${className.capitalize()}): ResponseEntity<Any>\n" +
                        "\n" +
                        "    @GetMapping(\"/${className.toLowerCase()}/download\")\n" +
                        "    fun download(@RequestParam name: String): ResponseEntity<Any>\n" +
                        "\n" +
                        "    @PostMapping(\"/${className.toLowerCase()}/upload\")\n" +
                        "    fun upload(@RequestParam file: MultipartFile): ResponseEntity<Any>\n" +
                        "}"
            return iDataController
        }

        fun generateController(className: String): String {
            val controller =
                "package com.demospring.${className.toLowerCase()}.api\n" +
                        "\n" +
                        "import com.demospring.controller.BaseController\n" +
                        "import com.demospring.${className.toLowerCase()}.model.${className.capitalize()}\n" +
                        "import com.demospring.${className.toLowerCase()}.service.${className.capitalize()}Service\n" +
                        "import org.apache.logging.log4j.ThreadContext\n" +
                        "import org.slf4j.LoggerFactory\n" +
                        "import org.springframework.http.HttpHeaders\n" +
                        "import org.springframework.http.ResponseEntity\n" +
                        "import org.springframework.web.bind.annotation.*\n" +
                        "import org.springframework.web.multipart.MultipartFile\n" +
                        "\n" +
                        "@RestController\n" +
                        "//@RequestMapping(\"/api/${className.toLowerCase()}\")\n" +
                        "class ${className.capitalize()}Controller(private val service: ${className.capitalize()}Service): BaseController(), IDataController {\n" +
                        "\tcompanion object {\n" +
                        "\t\tprivate val logger = LoggerFactory.getLogger(${className.capitalize()}Controller::class.java)\n" +
                        "\t}\n" +
                        "\n" +
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@GetMapping(\"/${className.toLowerCase()}\", produces = [\"application/json\"])\n" +
                        "\toverride fun findAll(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) _page: Int?, @RequestParam(required = false) _limit: Int?): ResponseEntity<MutableIterable<${className.capitalize()}>> {\n" +
                        "\t\tif (activityId != null) {\n" +
                        "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                        "\t\t}\n" +
                        "\t\tlogger.info(\"findAll started\")\n" +
                        "\n" +
                        "\t\tval ${className.toLowerCase()} = service.findAll(_page, _limit)\n" +
                        "\n" +
                        "\t\tlogger.info(\"findAll ended\")\n" +
                        "\t\treturn ResponseEntity.ok().body(${className.toLowerCase()})\n" +
                        "\t}\n" +
                        "\n" +
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@GetMapping(\"/${className.toLowerCase()}/count\", produces = [\"application/json\"])\n" +
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
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@GetMapping(\"/${className.toLowerCase()}/search\", produces = [\"application/json\"])\n" +
                        "\toverride fun search(@RequestHeader(required = false) activityId: String?, @RequestParam(required = false) q: String): ResponseEntity<Iterable<${className.capitalize()}>> {\n" +
                        "\t\tif (activityId != null) {\n" +
                        "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                        "\t\t}\n" +
                        "\t\tlogger.info(\"search started\")\n" +
                        "\t\tval ${className.toLowerCase()} = (service.findByQ(q))\n" +
                        "\t\tlogger.info(\"search ended\")\n" +
                        "\t\treturn ResponseEntity.ok().body(${className.toLowerCase()})\n" +
                        "\t}\n" +
                        "\n" +
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@GetMapping(\"/${className.toLowerCase()}/{id}\", produces = [\"application/json\"])\n" +
                        "\toverride fun findById(@RequestHeader(required = false) activityId: String?, @PathVariable id: Long): ResponseEntity<Any> {\n" +
                        "\t\tif (activityId != null) {\n" +
                        "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                        "\t\t}\n" +
                        "\t\tlogger.info(\"findById started\")\n" +
                        "\n" +
                        "\t\tval ${className.toLowerCase()} = service.findById(id)\n" +
                        "\t\tlogger.info(\"findById ended\")\n" +
                        "\t\treturn ResponseEntity.ok().body(${className.toLowerCase()})\n" +
                        "\t}\n" +
                        "\n" +
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@PostMapping(\"/${className.toLowerCase()}\", produces = [\"application/json\"])\n" +
                        "\toverride fun insert(@RequestHeader(required = false) activityId: String?, @RequestBody ${className.toLowerCase()}Req: ${className.capitalize()}): ResponseEntity<Any> {\n" +
                        "\t\tif (activityId != null) {\n" +
                        "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                        "\t\t}\n" +
                        "\t\tlogger.info(\"insert started\")\n" +
                        "\n" +
                        "\t\tval new${className.capitalize()} = service.insert(${className.toLowerCase()}Req)\n" +
                        "\t\tlogger.info(\"insert ended\")\n" +
                        "\t\treturn ResponseEntity.ok().body(new${className.capitalize()})\n" +
                        "\t}\n" +
                        "\n" +
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@DeleteMapping(\"/${className.toLowerCase()}/{id}\", produces = [\"application/json\"])\n" +
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
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@PutMapping(\"/${className.toLowerCase()}/{id}\", produces = [\"application/json\"])\n" +
                        "\toverride fun update(@RequestHeader(required = false)  activityId: String?, @PathVariable id: Long, @RequestBody ${className.toLowerCase()}Req: ${className.capitalize()}): ResponseEntity<Any> {\n" +
                        "\t\tif (activityId != null) {\n" +
                        "\t\t\tThreadContext.put(\"activityId\", activityId)\n" +
                        "\t\t}\n" +
                        "\t\tlogger.info(\"update started\")\n" +
                        "\n" +
                        "\t\tval new${className.capitalize()} = service.update(id=id, ${className.toLowerCase()} = ${className.toLowerCase()}Req)\n" +
                        "\t\tlogger.info(\"update ended\")\n" +
                        "\t\treturn ResponseEntity.ok().body(new${className.capitalize()})\n" +
                        "\t}\n" +
                        "\n" +
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@GetMapping(\"/${className.toLowerCase()}/download\")\n" +
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
                        "\t@CrossOrigin(origins = arrayOf(\"http://localhost:3000\"))\n" +
                        "\t@PostMapping(\"/${className.toLowerCase()}/upload\")\n" +
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
            return controller
        }

        fun generateBootstrap(className: String): String {
            val bootstrap =
                "package com.demospring.${className.toLowerCase()}.bootstrap\n" +
                        "\n" +
                        "import com.demospring.${className.toLowerCase()}.service.${className.capitalize()}Service\n" +
                        "import org.slf4j.LoggerFactory\n" +
                        "import org.springframework.context.ApplicationListener\n" +
                        "import org.springframework.context.annotation.Profile\n" +
                        "import org.springframework.context.event.ContextRefreshedEvent\n" +
                        "import org.springframework.stereotype.Component\n" +
                        "\n" +
                        "@Component\n" +
                        "@Profile(\"default\")\n" +
                        "class ${className.capitalize()}Bootstrap(val ${className.toLowerCase()}Service: ${className.capitalize()}Service) : ApplicationListener<ContextRefreshedEvent> {\n" +
                        "    companion object {\n" +
                        "        private val logger = LoggerFactory.getLogger(${className.capitalize()}Bootstrap::class.java)\n" +
                        "    }\n" +
                        "    override fun onApplicationEvent(event: ContextRefreshedEvent) {\n" +
                        "       // fetch all ${className.toLowerCase()}\n" +
                        "        logger.info(\"${className.capitalize()}s found with findAll():\")\n" +
                        "        logger.info(\"-------------------------------\")\n" +
                        "        ${className.toLowerCase()}Service.findAll().forEach { logger.info(it.toString()) }\n" +
                        "        logger.info(\"\")\n" +
                        "    }\n" +
                        "}"
            return bootstrap
        }

        fun generateModel(className: String, typeList: List<Type>): String {
            val sb = StringBuilder()
            sb.appendln(
                "package com.demospring.${className.toLowerCase()}.model\n" +
                        "import java.time.Instant\n" +
                        "import java.util.*\n"+
                        "import javax.persistence.*\n" +
                        "import java.time.LocalDate\n" +
                        "\n" +
                        "@Entity\n" +
                        "data class ${className.capitalize()}("
            )
            typeList.forEach {
                if (it.fieldName == "id") {
                    sb.appendln(
                        "\n\t\t@Id @GeneratedValue(strategy = GenerationType.IDENTITY)\n" +
                                "\t\t@Column(name=\"id\", unique = true, nullable = false)\n" +
                                "\t\tval id: Long = -1,"
                    )
                } else {
                    val fieldType = if(it.fieldType == "date") "LocalDate" else it.fieldType.capitalize()
                    sb.appendln(
                        "\n\t\t@Column(name=\"${it.fieldName.toLowerCase()}\")\n" +
                                "\t\tval ${it.fieldName.toLowerCase()}: ${fieldType},"
                    )
                }
            }
            sb[sb.length - 2] = ')'

            return sb.toString()
        }

        fun generateRepository(className: String): String {
            val repository =
                "package com.demospring.${className.toLowerCase()}.service\n" +
                        "\n" +
                        "import com.demospring.${className.toLowerCase()}.model.${className.capitalize()}\n" +
                        "import org.springframework.data.repository.CrudRepository\n" +
                        "import org.springframework.data.repository.PagingAndSortingRepository\n" +
                        "\n" +
                        "interface ${className.capitalize()}Repository : CrudRepository<${className.capitalize()}, Long>, PagingAndSortingRepository<${className.capitalize()}, Long> {\n" +
                        "\n" +
                        "}\n"
            return repository
        }

        fun generateDataService(className: String, typeList: List<Type>): String {
            val sb = StringBuilder()
            sb.appendln(
                "package com.demospring.${className.toLowerCase()}.service\n" +
                        "\n" +
                        "import com.demospring.${className.toLowerCase()}.model.${className.capitalize()}\n" +
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
                        "import java.time.format.DateTimeFormatter" +
                        "\n" +
                        "@Service\n" +
                        "class ${className.capitalize()}Service(private val repository: ${className.capitalize()}Repository) : IDataService {\n" +
                        "    override fun findByQ(q: String): Iterable<${className.capitalize()}> {\n" +
                        "        TODO(\"Not implemented\")\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun findAll(): MutableIterable<${className.capitalize()}> {\n" +
                        "        return repository.findAll()\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun findAll(page:Int?, size:Int?): MutableIterable<${className.capitalize()}> {\n" +
                        "        if(page == null || size == null) {\n" +
                        "            return findAll()\n" +
                        "        }\n" +
                        "        val pages = PageRequest.of(page,size)\n" +
                        "        return repository.findAll(pages).content\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun findById(id: Long): Optional<${className.capitalize()}> {\n" +
                        "        val ${className.toLowerCase()} = repository.findById(id)\n" +
                        "        if(!${className.toLowerCase()}.isPresent) {\n" +
                        "            throw NotFoundException(\"${className.capitalize()} id: \$id not found\")\n" +
                        "        }\n" +
                        "        return ${className.toLowerCase()}\n" +
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
                        "    override fun update(id: Long, ${className.toLowerCase()}: ${className.capitalize()}): ${className.capitalize()}? {\n" +
                        "        if(id != ${className.toLowerCase()}.id) {\n" +
                        "            throw IllegalArgumentException(\"update with id: \$id is not the same as \${${className.toLowerCase()}.id}\")\n" +
                        "        }\n" +
                        "        validateId(id)\n" +
                        "\n" +
                        "        return repository.save(${className.toLowerCase()})\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun insert(${className.toLowerCase()}: ${className.capitalize()}) : ${className.capitalize()}?  {\n" +
                        "        if(repository.existsById(${className.toLowerCase()}.id)) {\n" +
                        "            throw IllegalArgumentException(\"${className.capitalize()} id: \${${className.toLowerCase()}.id} already in database\")\n" +
                        "        }\n" +
                        "\n" +
                        "        return repository.save(${className.toLowerCase()})\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun getCSV(): String {"
            )

            sb.append("        val header = \"")
            typeList.forEach {
                sb.append("${it.fieldName}, ")
            }
            sb[sb.length - 2] = '"'
            sb[sb.length - 1] = '\n'
            sb.appendln(
                "        val all = findAll()\n" +
                        "        val sb = StringBuilder()\n" +
                        "        sb.appendln(header)\n" +
                        "        all.forEach {"
            )

            sb.append("            sb.appendln(\"")
            typeList.forEach {
                sb.append("\${it.${it.fieldName}}, ")
            }
            sb[sb.length - 2] = '"'
            sb[sb.length - 1] = ')'


            sb.appendln(
                "\n        }\n" +
                        "        return sb.toString()\n" +
                        "    }\n" +
                        "\n" +
                        "    override fun saveCSV(fileInput: InputStream): MutableList<${className.capitalize()}?> {\n" +
                        "        val fileData = String(fileInput.readBytes(), StandardCharsets.UTF_8)\n" +
                        "        val dataList = fileData.split(System.lineSeparator())\n" +
                        "        val result = mutableListOf<${className.capitalize()}?>()\n" +
                        "        dataList.forEachIndexed { index, s ->\n" +
                        "            if (index == 0) {\n" +
                        "                return@forEachIndexed\n" +
                        "            }\n" +
                        "            if (s.isNullOrEmpty()) {\n" +
                        "                return@forEachIndexed\n" +
                        "            }\n" +
                        "            val parsed = parseLine(s)\n"
            )
            sb.appendln("            val new${className.capitalize()} = ${className.capitalize()}(")

            /**
            val newPerson2 = Person2(id = -1, firstname = parsed[1], lastname = parsed[2],
            birthdate = convertStringToObject("java.time.LocalDate",parsed[3]) as LocalDate,
            ismodified = convertStringToObject("kotlin.Boolean",parsed[4]) as Boolean,
            timestamp = convertStringToObject("java.time.Instant",parsed[5]) as Instant,
            salary = convertStringToObject("kotlin.Double",parsed[6]) as Double)
             */

            typeList.forEachIndexed { index, t ->
                if (t.fieldName == "id") {
                    sb.appendln("\t\t\tid = -1, ")
                } else {
                    when (t.fieldType) {
                        "date" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"java.time.LocalDate\",parsed[$index]) as LocalDate, ")
                        "boolean" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Boolean\",parsed[$index]) as Boolean, ")
                        "instant" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"java.time.Instant\",parsed[$index]) as Instant, ")
                        "double" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Double\",parsed[$index]) as Double, ")
                        "int" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Int\",parsed[$index]) as Double, ")
                        "long" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Long\",parsed[$index]) as Double, ")
                        "short" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Short\",parsed[$index]) as Double, ")
                        "byte" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Byte\",parsed[$index]) as Double, ")
                        "float" -> sb.appendln("\t\t\t${t.fieldName} = convertStringToObject(\"kotlin.Float\",parsed[$index]) as Double, ")
                        else-> sb.appendln("\t\t\t${t.fieldName} = parsed[$index], ")
                    }
                }
            }
            sb[sb.length - 3] = ')'
            sb[sb.length - 2] = '\n'

            sb.appendln(
                "            val ${className.toLowerCase()} = insert(new${className.capitalize()})\n" +
                        "            result.add(${className.toLowerCase()})\n" +
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
                        "    }"+
                        "    private fun parseLine(line:String):List<String>{\n" +
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
                        "            throw NotFoundException(\"${className.capitalize()} id: \$id not found\")\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"
            )
            return sb.toString()

        }

        fun generateIDataService(className: String): String {
            val dataService =
                "package com.demospring.${className.toLowerCase()}.service\n" +
                        "\n" +
                        "import com.demospring.${className.toLowerCase()}.model.${className.capitalize()}\n" +
                        "import java.io.InputStream\n" +
                        "import java.util.*\n" +
                        "\n" +
                        "interface IDataService {\n" +
                        "    fun findByQ(q: String): Iterable<${className.capitalize()}>\n" +
                        "    fun findAll(): MutableIterable<${className.capitalize()}>\n" +
                        "    fun findAll(page: Int?, size: Int?): MutableIterable<${className.capitalize()}>\n" +
                        "    fun findById(id: Long): Optional<${className.capitalize()}>\n" +
                        "    fun deleteById(id: Long)\n" +
                        "    fun count(): Long\n" +
                        "    fun update(id: Long, ${className.toLowerCase()}: ${className.capitalize()}): ${className.capitalize()}?\n" +
                        "    fun insert(${className.toLowerCase()}: ${className.capitalize()}) : ${className.capitalize()}?\n" +
                        "    fun getCSV(): String\n" +
                        "    fun saveCSV(fileInput: InputStream): MutableList<${className.capitalize()}?>\n" +
                        "}"
            return dataService
        }

        fun generateSpringPackage(className: String, targetFolder: String, typeList: List<Type>) {

            val packageFolder = PATH_PACKAGE_SPRING_API
            generateController(className, targetFolder, packageFolder)

            generateBootstrap(className,targetFolder,packageFolder)

            generateModel(className,targetFolder,packageFolder, typeList)

            generateService(className,targetFolder,packageFolder, typeList)
        }

        private fun generateController(className: String, targetFolder: String, packageFolder: String) {
            val apiDataConntroller = generateApiDataController(className)
            val controller = generateController(className)
            val pathApi = "$targetFolder/$packageFolder/$className/$SPRING_API/"
            File(pathApi).mkdirs()
            val iDataControllerFileName = "$pathApi/IDataController.kt"
            File(iDataControllerFileName).writeText(apiDataConntroller)

            val controllerFileName = "$pathApi/${className.capitalize()}Controller.kt"
            File(controllerFileName).writeText(controller)
        }

        private fun generateBootstrap(className: String, targetFolder: String, packageFolder: String) {
            val bootstrap = generateBootstrap(className)
            val pathBootstrap = "$targetFolder/$packageFolder/$className/$SPRING_BOOTSTRAP/"
            File(pathBootstrap).mkdirs()
            val bootstrapFileName = "$pathBootstrap/${className.capitalize()}Bootstrap.kt"
            File(bootstrapFileName).writeText(bootstrap)
        }

        private fun generateModel(className: String, targetFolder: String, packageFolder: String , typeList: List<Type>) {
            val model = generateModel(className, typeList)
            val pathModel = "$targetFolder/$packageFolder/$className/$SPRING_MODEL/"
            File(pathModel).mkdirs()
            val modelFileName = "$pathModel/${className.capitalize()}.kt"
            File(modelFileName).writeText(model)
        }

        private fun generateService(className: String, targetFolder: String, packageFolder: String , typeList: List<Type>) {
            val repository = generateRepository(className)
            val service = generateDataService(className,typeList)
            val iDataService = generateIDataService(className)

            val pathService = "$targetFolder/$packageFolder/$className/$SPRING_SERVICE/"
            File(pathService).mkdirs()

            val repositoryFileName = "$pathService/${className.capitalize()}Repository.kt"
            File(repositoryFileName).writeText(repository)

            val serviceFileName = "$pathService/${className.capitalize()}Service.kt"
            File(serviceFileName).writeText(service)

            val dataServiceFileName = "$pathService/IDataService.kt"
            File(dataServiceFileName).writeText(iDataService)
        }

        fun generateSpringFiles(targetDescriptor: String, targetFolder: String) {
            copyFiles(targetFolder=targetFolder)
            val fileList = File(targetDescriptor).walk().filter{it.name.contains(".txt")}
            fileList.forEach {
                val className = it.nameWithoutExtension
                val descriptorFile = "$targetDescriptor/${it.name}"
                val descriptorList = Descriptor.getDescriptorFromFile(descriptorFile)
                require(descriptorList.isNotEmpty()) {"descriptorList is empty"}

                generateSpringPackage(className = className, targetFolder = targetFolder, typeList = descriptorList)
            }
        }

        const val PATH_PACKAGE_SPRING_API = "/src/main/kotlin/com/demospring"
        const val SPRING_FILES = "kotlinapi"
        const val SPRING_API = "api"
        const val SPRING_BOOTSTRAP = "bootstrap"
        const val SPRING_MODEL = "model"
        const val SPRING_SERVICE = "service"

    }
}