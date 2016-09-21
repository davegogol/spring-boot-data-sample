#Rest service sample 
Rest service sample which provides some banking sample endpoints. 

#Endpoints:

* <code>PUT</code> /transactionservice/transaction/{id} - Stores a new transaction details;
```bash
Body:
{ "amount":double,"type":string,"parent_id":long }
```
* <code>GET</code> /transactionservice/transaction/{id} - Returns transaction details;
```bash
Returns:
{ "amount":double,"type":string,"parent_id":long }
```

* <code>GET</code> /transactionservice/types/{types} - Returns transactions IDs that belong to the same type;
```bash
* Returns:
[ long, long, .... ]
```

* <code>GET</code> /transactionservice/sum/{transaction_id} - Returns a sum of all transactions that are linked by their parent id to transaction id;
```bash
Returns
{ "sum", double }
```

#Running:

## Requirements
- Java 1.8

```bash
mvn spring-boot:run
```
# Testing

```bash
curl '$SERVICE_URL/transactionservice/sum/5'
```
A successful response looks like:
```bash
{ "sum", 0.0 }
```