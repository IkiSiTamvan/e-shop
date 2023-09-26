# E-Shop

A service created with spring boot that manage customer, item, cart and transaction.  

With validation using spring boot validation, that validate the incoming request;
an example of standar Exception usage, and its handler;
some unitTest to ensure some main validation is checked when there are changes;

## API Reference

#### Create Item

```http
  POST /api/items
```

```json
{
  "name": "Lenovo Ideapad",
  "desc": "deskripsi dari lenovo ideapad",
  "category": "laptop, elektronik",
  "price": 5000000,
  "rating": 4.5
}
```

#### Get items

```http
  GET api/items?page=0&size=10
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `Integer` | **Optional**. default value 0 |
| `size` | `Integer` | **Optional**. default value 10 |

#### Create Customer

```http
  POST /api/customers
```

```json
{
  "name": "Rizky",
  "email": "rizky@gmail.com",
  "phone": "0888890098"
}
```

#### Add Cart Item 

```http
  POST /api/cart?custId=2&itemId=13&itemQty=2
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `custId` | `Integer` | **Required**. the Id from customer |
| `itemId` | `Integer` | **Required**. the Id from item |
| `itemQty` | `Integer` | **Optional**. default value 1 |

it will check if the customer and item exist, then check the stock of the item before adding it to cart. 

The customerId and itemId has multi column unique constraint, meaning the table will not record new record if it already has a record with same customerId and itemId, it will only update the quantity instead if the quantity changes.

#### Add Cart Items 

```http
  POST /api/cart/items
```
```json
{
  "customerId": 2,
  "name": "Rizky",
  "email": "rizky@gmail.com",
  "phone": "0888890098"
  "items": [
    {
      "itemId": 12,
      "quantity": 1
    },
    {
      "itemId": 13,
      "quantity": 2
    },
    {
      "itemId": 11,
      "quantity": 2
    }
  ]
}
```
same with add chart item, but this one add multiple item at once. Multi column unique constraint still apply.

#### Delete Cart Item 

```http
  DELETE /api/cart?custId=2&itemId=13
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `custId` | `Integer` | **Required**. the Id from customer |
| `itemId` | `Integer` | **Required**. the Id from item |

Delete item from customer cart

#### Get Cart Item 

```http
  GET /api/api/cart/customer/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Integer` | **Required**. the Id from customer |

Get items from customer cart 

#### Finalize / Calculate cart 

```http
  POST /api/transaction/customer/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Integer` | **Required**. the Id from customer |

it will create the transaction record, get all item from this customer cart, save all item and calculate its total price (qty * price) in transaction detail table.

after successfully save transaction detail, it will remove all the cart item for this customer, and finally update the total price and payment status in transaction table.
