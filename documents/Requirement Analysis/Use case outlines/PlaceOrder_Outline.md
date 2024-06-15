# Outline for all the use cases to place an order
## "Place Order" Use Case
- Use case ID: UC001
- Name of use case:	Place order
- Actor: Customer
- Brief Description: This use case describes the interaction between a customer and AIMS when the customer wants to place an order.
- Pre-condition: The customer has products in the cart.
- Post-condition: None
### Basic flow (Complete): 
1. Customer checks cart

2. AIMS checks the remaining quantity product

3. AIMS displays cart

4. Customer requests order

5. AIMS displays delivery information form

6. Customer fills and confirms the delivery information

7. AIMS calculates shipping fee, display receipt

8. Customer confirms the order

9. AIMS calls UC002 "Pay order"

10. AIMS displays the order information

11. AIMS sends all order and transaction information to the customer's email

12. AIMS notifies successful order

### Alternative flow:
3a. AIMS shows notification of insufficient quantity of product in stock and requests customer to update the cart 

3b. Customer updates cart

6a. Customer selects rush order delivery

6b. AIMS checks the product for rush order delivery

6c. AIMS inserts UC003 "Select rush order delivery" if any product supports rush order delivery

7a. AIMS requests customer to re-enter and update the transaction information
## "Pay Order" Use Case
- Use case ID: UC002
- Name of use case: Pay order
- Actor: Customer, VNPay
- Brief Description: This use case describes the interaction between the customer, VNPay and AIMS when the customer wants to make a payment
- Pre-condition: AIMS has calculated the total price to be paid
- Post-condition: None
### Basic flow (Complete): 
1. AIMS displays the payment screen 

2. Customer enters card information and confirms transaction

3. AIMS validates the payment information

4. VNPay processes the transaction

5. AIMS records the transaction information
### Alternative flow:
2a. Customer cancels the payment at any time

5a. AIMS notifies the invalid information field or insufficient payment value

5b. Customers updates the information 

## "Place Rush Order" Use Case
- Use case ID: UC003
- Name of use case: Select rush order delivery
- Actor: Customer
- Brief Description: This use case describes the interaction between the customer and AIMS when the customer wants to make a rush order delivery.
- Pre-condition: Customer chooses rush order option.
- Post-condition: None
### Basic flow (Complete): 
1. AIMS	displays the rush order delivery screen with a list of products that support rush order delivery

2. Customer	updates rush order information and chooses products

3. AIMS calculates shipping costs
### Alternative flow:
1a. Customer cancels the option at any time

3a. AIMS notifies the unsupported delivery for areas outside Ha Noi

3b. AIMS requests customer to fill the missing information