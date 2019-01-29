
                                        ABC Bank Token Generation System
        
API Documentation<br />                                                    
The API documentation is placed in repo folder docs/javadoc 

Class Diagram <br />
The class diagram is in file AbcBankClassDiagram.jpg 

Data Model

![ABC Bank Data Model](/AbcBankErDiagram.png?raw=true)

1. Counter Layer
  Counter layer has the Counter table and the table Counter_services which maps to the services a particular counter is serving.
  
2. Customer Layer
  Customer layer has the customer table and the accounts associated with the customer.
  
3. Services Layer
    Services layer has the services and multicounter services the bank is offerting. The multi-counter services are divided into services and Multi_counter_service_has_services adds maps mutli-counter service to multiple services.
    
4. Branch Layer
    Branch layer has the branch table and Branch_services and Branch_multi_counter_services which maps to the services and the multi-counter services the branch is serving.
    
5. Token Layer
  Token Layer follwing tables 
  i. Token table which contains the information about token(customer, branch, status, priority etc). 
  ii. Token_services table which contains the information about the services a customer has selected for the token along with the order in which the service needs to be processed.
  iii. Token_multi_counter_services table which contains the information about the multi counter services a particular customer has selected along with the order in which these multi-counter services needs to be processed.
  iv. Token_processing_steps has the details about the each step the token processing is taking. The highest order service/multi-counter service will be taken and it will be divided into steps which is required to process this service/multi-counter service along with order of steps. Each step is processed individually and then next service with highest order is picked and the process is repeated.
  
  
