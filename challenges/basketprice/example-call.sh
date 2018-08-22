#!/bin/sh
curl -k -d '{ "items": ["Milk","Milk","Milk","Apples"] , "currency": "EUR" }' -H "Content-Type: application/json" -u user:passw0rd  -X POST https://localhost:3000/api/price
