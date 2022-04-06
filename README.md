# virtuslab-internship-task

### How to test the API

**Show contents of the basket**

` curl -v localhost:8080/cart `


**Add a product to the basket**


`curl -X POST localhost:8080/addProductToCart -H 'Content-type:application/json' -d '<ProductName>' `

**Print a reciept with a specified discount applied**

`curl -v localhost:8080/generateReceipt/<DiscountName>`

**Print a reciept withount any discounts applied**

`curl -v localhost:8080/generateReceipt`
