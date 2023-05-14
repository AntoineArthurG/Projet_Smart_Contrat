# -*- coding: utf-8 -*-
import shutil
import os
import io
import sys
import json
import folium
import requests
from folium.plugins.draw import Draw
import pandas as pd
from PyQt5.QtWidgets import QApplication, QVBoxLayout, QWidget  # , QFileDialog
from PyQt5.QtWebEngineWidgets import QWebEngineView


class CodificationPalabre():
    def __init__(self,  geojsonFile):
        self.geojsonfile = geojsonFile

    def createDelimitation(self):
        
        url = "http://localhost:8080/polygons" # URL de l’API à appeler
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
        


class Mapy(QWidget):
    def __init__(self,name: str = "data", parent=None):
        super(Mapy, self).__init__(parent)
        self.temp_file = "metadata"
        self.save_file = f"{name}.geojson"
        self.interfejs()

    def interfejs(self):
        vbox = QVBoxLayout(self)
        self.webEngineView = QWebEngineView()
        self.webEngineView.page().profile().downloadRequested.connect(
            self.handle_downloadRequested
        )

        self.loadPage()
        vbox.addWidget(self.webEngineView)
        self.setLayout(vbox)
        self.setGeometry(700, 700, 700, 700)
        self.setWindowTitle("TP")
        self.show()

    def loadPage(self):
        m = folium.Map(location=[-21.6166700, 166.2166700], zoom_start=17)  # , crs="EPSG3163")

        Draw(
            export=True,
            filename=self.temp_file,
            position="topleft",
            draw_options={
                "polyline": False,
                "rectangle": False,
                "circle": False,
                "circlemarker": False,
            },
            edit_options={"poly": {"allowIntersection": False}},
        ).add_to(m)

        folium.TileLayer(
            tiles='https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}',
            attr='Esri',
            name='Esri Satellite',
            overlay=False,
            control=True
        ).add_to(m)

        folium.LatLngPopup().add_to(m)

        data = io.BytesIO()
        m.save(data, close_file=False)

        self.webEngineView.setHtml(data.getvalue().decode())

    def load_data(self):
        datas = pd.read_json(self.temp_file)
        shutil.copy2(self.temp_file, self.save_file)
        os.remove(self.temp_file)

        my_list = []
        for gpsPoint in datas['features']:
            my_list.append(gpsPoint["geometry"]["coordinates"])

        self.my_coords = my_list
        with open(f"list_{self.save_file}", "w") as f:
            f.write(json.dumps(my_list, indent=2))

    def handle_downloadRequested(self, item):
    
        item.setPath(self.save_file)
        item.accept() 
        datas = pd.read_json(self.save_file)
       
        #my_list = []
        #for gpsPoint in datas['features'] : 
        #    print("point", gpsPoint["geometry"]["coordinates"] )
        #    my_list.append(gpsPoint["geometry"]["coordinates"] )


if __name__ == "__main__":
    app = QApplication(sys.argv)
    #name = input("Nom de la personne : ")
    #name = f"{name}"
    name =  "data"
    okno = Mapy(name=name)
    sys.exit(app.exec_())
