# CorDapp Todo - Kotlin

Welcome to the Kotlin CorDapp Todo. The CorDapp Todo is a stubbed-out CorDapp that you can use to bootstrap 
your own CorDapps.

# Use case

In this cordapp demonstrates a single node create todo task items. The following operations are available

* Create - Creates a new task with unconsumed state
* Cancel - Cancel or unCancel task  by consuming the previous state and create new entry with unconsumed state
* Complete - Complete the task by consuming the previous state 


# Pre-Requisites

See https://docs.corda.net/getting-set-up.html.

# Usage

## Running tests inside IntelliJ

We recommend editing your IntelliJ preferences so that you use the Gradle runner - this means that the quasar utils
plugin will make sure that some flags (like ``-javaagent`` - see below) are
set for you.

To switch to using the Gradle runner:

* Navigate to ``Build, Execution, Deployment -> Build Tools -> Gradle -> Runner`` (or search for `runner`)
  * Windows: this is in "Settings"
  * MacOS: this is in "Preferences"
* Set "Delegate IDE build/run actions to gradle" to true
* Set "Run test using:" to "Gradle Test Runner"

If you would prefer to use the built in IntelliJ JUnit test runner, you can run ``gradlew installQuasar`` which will
copy your quasar JAR file to the lib directory. You will then need to specify ``-javaagent:lib/quasar.jar``
and set the run directory to the project root directory for each test.

## Build
    gradlew clean build deployNodes

## Running the nodes

    ./buid/nodes/runnodes

## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Tue Nov 06 11:58:13 GMT 2018>>>

You can use this shell to interact with your node. For example, enter `run networkMapSnapshot` to see a list of 
the other nodes on the network:

    Tue Nov 06 11:58:13 GMT 2018>>> run networkMapSnapshot
    - addresses:
      - "localhost:10005"
      legalIdentitiesAndCerts:
      - "O=PartyA, L=London, C=GB"
      platformVersion: 4
      serial: 1573427519078
    - addresses:
      - "localhost:10002"
      legalIdentitiesAndCerts:
      - "O=Notary, L=London, C=GB"
      platformVersion: 4
      serial: 1573427517002
    - addresses:
      - "localhost:10008"
      legalIdentitiesAndCerts:
      - "O=PartyB, L=New York, C=US"
      platformVersion: 4
      serial: 1573427518833
    
    Tue Nov 06 12:30:11 GMT 2018>>> 
    
flow list

    com.todo.flows.CancelFlow$Cancelender
    com.todo.flows.CompleteFlow$CompleteSender
    com.todo.flows.CreateFlow$CreateSender
    net.corda.core.flows.ContractUpgradeFlow$Authorise
    net.corda.core.flows.ContractUpgradeFlow$Deauthorise
    net.corda.core.flows.ContractUpgradeFlow$Initiate
    
run nodeInfo

addresses:

    - "localhost:10005"
    legalIdentitiesAndCerts:
    - "O=PartyA, L=London, C=GB"
    platformVersion: 4
    serial: 1573427519078

flow start com.todo.flows.CreateFlow$CreateSender task: "Buy milk"


     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
    ➡️   Done
    Flow completed with result: SignedTransaction(id=6C85242C35955FD47066515A26582769B731EB74F0F5DA128C121D62C2191724)

flow start  com.todo.flows.CompleteFlow$CompleteSender taskId: 0b18467a-e1c3-4b1c-9f87-ddf37eddd328


     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
             ✅   Requesting signature by Notary service
             ✅   Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
    ➡️   Done
    Flow completed with result: SignedTransaction(id=5A87ED6BF0A87DAC0E96815D1EAC8E39B50F739E56582A32AD150E3E32CE122F)

PARTYA

    SELECT VS.*, TS.*, NS.*
        FROM VAULT_STATES VS
        INNER JOIN TODO_STATE TS on VS.OUTPUT_INDEX = TS.OUTPUT_INDEX AND VS.TRANSACTION_ID = TS.TRANSACTION_ID
        INNER JOIN NODE_TRANSACTIONS NS on VS.TRANSACTION_ID = NS.TX_ID
    
NOTARY
    
    SELECT NC.*, NNCS.*, NNRL.*
        FROM NODE_NOTARY_COMMITTED_TXS NC
            INNER JOIN NODE_NOTARY_COMMITTED_STATES NNCS on NC.TRANSACTION_ID = NNCS.CONSUMING_TRANSACTION_ID
            INNER JOIN NODE_NOTARY_REQUEST_LOG NNRL on NNCS.CONSUMING_TRANSACTION_ID = NNRL.CONSUMING_TRANSACTION_ID    


VAULTQUERY

    run vaultQuery contractStateType: com.todo.states.TodoState
    
    states:
    - state:
        data: !<com.todo.states.TodoState>
          me: "O=PartyA, L=London, C=GB"
          task: "Buy milk"
          status: "New"
          entryDateTime: "2019-11-12T00:47:36.997Z"
          linearId:
            externalId: null
            id: "c6f1d1e0-0881-4fa0-ac8a-632f39d67c5a"
          participants:
          - "O=PartyA, L=London, C=GB"
        contract: "com.todo.contracts.TodoContract"
        notary: "O=Notary, L=London, C=GB"
        encumbrance: null
        constraint: !<net.corda.core.contracts.SignatureAttachmentConstraint>
          key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
      ref:
        txhash: "983AFC1175AF46C318C95B67E89835C62BFB7D0D16DD348579A4C24AF7A7452B"
        index: 0
    statesMetadata:
    - ref:
        txhash: "983AFC1175AF46C318C95B67E89835C62BFB7D0D16DD348579A4C24AF7A7452B"
        index: 0
      contractStateClassName: "com.todo.states.TodoState"
      recordedTime: "2019-11-12T00:47:38.130Z"
      consumedTime: null
      status: "UNCONSUMED"
      notary: "O=Notary, L=London, C=GB"
      lockId: null
      lockUpdateTime: null
      relevancyStatus: "RELEVANT"
      constraintInfo:
        constraint:
          key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
    totalStatesAvailable: -1
    stateTypes: "UNCONSUMED"
    otherResults: []

 AddParticipantsFlow   
    flow start com.todo.flows.AddParticipantsFlow$AddParticipantsSender taskId: 5e7710aa-06c2-475f-a551-427326ccaff8, participant: "O=PartyB,L=New York,C=US"
     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
             ✅   Requesting signature by Notary service
             ✅   Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
          Collecting counterparty signature
              Collecting signatures from counterparties.
              Verifying collected signatures.
    ➡️   Done
Flow completed with result: SignedTransaction(id=F1C4491444DB53EF49900D6376EE8BCC6C146EDCB9E9447A3DEF7801E8787ABE)


    
vaultQueryByCriteria ???

    run vaultQueryByCriteria contractStateType: com.todo.states.TodoState, criteria: { status: Vault.StateStatus.CONSUMED }

You can find out more about the node shell [here](https://docs.corda.net/shell.html).

flow start com.todo.flows.AddParticipantsFlow$AddParticipantsSender taskId: 5e7710aa-06c2-475f-a551-427326ccaff8, participant: "O=PartyB,L=New York,C=US"

     ✅   Starting
     ✅   Build the transaction
     ✅   Verify the transaction
     ✅   Sign the transaction
         ✅   Requesting signature by notary service
             ✅   Requesting signature by Notary service
             ✅   Validating response from Notary service
         ✅   Broadcasting transaction to participants
          Finalising transaction
              Requesting signature by notary service
                  Requesting signature by Notary service
                  Validating response from Notary service
              Broadcasting transaction to participants
          Collecting counterparty signature
              Collecting signatures from counterparties.
              Verifying collected signatures.
    ➡️   Done
    Flow completed with result: SignedTransaction(id=2E546841E9D0B369E9CBB95E8DC61E0F69492357DF32FEFEA38C5AE54D955A56)

flow start com.todo.flows.RemoveParticipantsFlow$RemoveParticipantsSender taskId: 5d71daa3-19d8-4f0c-8b93-44ad247df1df

 ✅   Starting
 ✅   Build the transaction
 ✅   Verify the transaction
 ✅   Sign the transaction
     ✅   Requesting signature by notary service
         ✅   Requesting signature by Notary service
         ✅   Validating response from Notary service
     ✅   Broadcasting transaction to participants
      Finalising transaction
          Requesting signature by notary service
              Requesting signature by Notary service
              Validating response from Notary service
          Broadcasting transaction to participants
      Collecting counterparty signature
          Collecting signatures from counterparties.
          Verifying collected signatures.
➡️   Done
Flow completed with result: SignedTransaction(id=905B4F0D7D17AB7DCF812166CB7E7CAA706ABEE5D5AA39A6A9EFB3805E681D90)

### Client

`clients/src/main/kotlin/com/Todo/Client.kt` defines a simple command-line client that connects to a node via RPC 
and prints a list of the other nodes on the network.

#### Running the client

##### Via the command line

Run the `runTodoClient` Gradle task. By default, it connects to the node with RPC address `localhost:10006` with 
the username `user1` and the password `test`.

##### Via IntelliJ

Run the `Run Todo Client` run configuration. By default, it connects to the node with RPC address `localhost:10006` 
with the username `user1` and the password `test`.

### Webserver

`clients/src/main/kotlin/com/Todo/webserver/` defines a simple Spring webserver that connects to a node via RPC and 
allows you to interact with the node over HTTP.

The API endpoints are defined here:

     clients/src/main/kotlin/com/Todo/webserver/Controller.kt

And a static webpage is defined here:

     clients/src/main/resources/static/

#### Running the webserver

##### Via the command line

Run the `runTodoServer` Gradle task. By default, it connects to the node with RPC address `localhost:10006` with 
the username `user1` and the password `test`, and serves the webserver on port `localhost:10050`.

##### Via IntelliJ

Run the `Run Todo Server` run configuration. By default, it connects to the node with RPC address `localhost:10006` 
with the username `user1` and the password `test`, and serves the webserver on port `localhost:10050`.

#### Interacting with the webserver

The static webpage is served on:

    http://localhost:10050

While the sole Todo endpoint is served on:

    http://localhost:10050/Todoendpoint
    
# Extending the Todo

You should extend this Todo as follows:

* Add your own state and contract definitions under `contracts/src/main/kotlin/`
* Add your own flow definitions under `workflows/src/main/kotlin/`
* Extend or replace the client and webserver under `clients/src/main/kotlin/`

For a guided example of how to extend this Todo, see the Hello, World! tutorial 
[here](https://docs.corda.net/hello-world-introduction.html).
