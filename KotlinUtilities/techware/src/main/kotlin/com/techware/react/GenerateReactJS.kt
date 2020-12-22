package com.techware.react

import com.techware.generate.Descriptor
import java.io.File

class GenerateReactJS {
    companion object {
        /**
         * This entrypoint to generate all react files
         * it requires two parameters
         * targetDescriptor: this is the directory containing files with .txt extension
         *      an example of file is person.txt that contains the following
         *          id, int
         *          firstname, string
         *          lastname, string
         *
         * targetFolder: this is the directory target that will be written with all files
         *
         * After running this method perform the following tasks
         *      npm install
         *          this will pull all the react dependencies
         *      npm start
         *          this will execute the react app
         */
        fun generateReactJSFiles(targetDescriptor: String, targetFolder: String) {
            copyFiles(targetFolder= targetFolder)
            val fileList = File(targetDescriptor).walk().filter{it.name.contains(".txt")}
            val classNames = fileList.map { it.nameWithoutExtension }.toList()
            generateActionReducerIndexFiles(targetFolder=targetFolder, classNameList = classNames)
            fileList.forEach {
                val className = it.nameWithoutExtension
                val descriptorFile = "$targetDescriptor/${it.name}"
                generateReactJS(fileName =descriptorFile,className = className,packageName = "",targetFolder= targetFolder)
            }
        }

        /**
         * copy a react template into the target folder
         */
        fun copyFiles(targetFolder: String) {
            File(ReactJSConstants.REACT_FILES).copyRecursively(File(targetFolder), true)
        }

        /**
         * generates edit and table pages
         */
        fun generateReactJS(fileName:String, className: String, packageName: String, targetFolder: String) {
            val descriptorList = Descriptor.getDescriptorFromFile(fileName)
            require(descriptorList.isNotEmpty()) {"descriptorList is empty"}

            GenerateReactJSEdit.generateReactJSEdit(className, targetFolder, descriptorList)

            GenerateReactJSTable.generateReactJSTable(className, targetFolder, descriptorList)

            GenerateReactJSReducers.generateReactJSReducersFiles(className, targetFolder)

            GenerateReactJSActions.generateReactJSActions(className, targetFolder)
        }

        /**
         * generate action, reducers and endPoints
         */

        fun generateActionReducerIndexFiles(targetFolder: String, classNameList: List<String>) {
            GenerateReactJSActions.generateActionIndexFile(classNameList, targetFolder)

            GenerateReactJSReducers.generateReducerIndexFile(classNameList, targetFolder)

            GenerateReactJSCommon.generateApiEndpointFile(classNameList, targetFolder)

            GenerateReactJSCommon.generateConstantsFile(classNameList, targetFolder)

            GenerateReactJSCommon.generateLayoutFile(classNameList, targetFolder)
        }
    }
}