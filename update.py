import csv
import json 
import requests

quantity = 431
version = 484

while quantity > 0:
	pload = {"id":1,"name": "Guitar","price":4000,"quantity":quantity,"description":"The best guitar","version":version}
	r = requests.post('http://localhost:8087/Market_war_exploded/updateProduct',json = pload)
	if r.status_code == 200:	
		version += 1
		quantity -= 1
	s = "Status: " + str(r.status_code) + ", Quantity: " + str(quantity)
	print(s)