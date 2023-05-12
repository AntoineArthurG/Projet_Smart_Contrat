import requests

url = f"http://localhost:8080/polygons" # URL de l’API à appeler

""" points = [
    [166.21704, -21.615572],
    [166.219218, -21.616968],
    [166.217426, -21.618046],
    [166.215205, -21.617367],
    [166.21704, -21.615572]
] # Liste des points à envoyer dans le corps de la requête """
""" points = {"coordinates":[
    [166.21704,-21.615572],
     [166.219218,-21.616968],
     [166.217426,-21.618046],
     [166.215205,-21.617367],
     [166.215827,-21.61619],
     [166.21704,-21.615572]]
     } """
points = {"coordinates" : [
    {
        "latitude": 166.21704,
        "longitude" : -21.615572
    },
    {
        "latitude": 166.219218,
        "longitude" : -21.616968
    },
    {
        "latitude": 166.217426,
        "longitude" : -21.618046
    },
    {
        "latitude": 166.215205,
        "longitude" : -21.617367
    },
    {
        "latitude": 166.215827,
        "longitude" : -21.61619
    },
    {
        "latitude": 166.21704,
        "longitude" : -21.615572
    }
]
}

# Envoi de la requête POST avec les points dans le corps de la requête
response = requests.post(url, json=points)

# Vérification du code de retour de la réponse
if response.status_code == requests.codes.ok:

    # La requête a été traitée avec succès
    print("Requête traitée avec succès.")
else:
    
    # La requête a échoué
    print("La requête a échoué avec le code de retour {}".format(response.status_code))
    