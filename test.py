# Test l'affichage de la map avec folium

import shutil
import os
import io
import sys
import json
import folium
from folium.plugins.draw import Draw
import pandas as pd
from PyQt5.QtWidgets import QApplication, QVBoxLayout, QWidget  # , QFileDialog
from PyQt5.QtWebEngineWidgets import QWebEngineView


temp_file = "metadata"
#m = folium.Map(location=[-21.6166700, 166.2166700], zoom_start=17)  # , crs="EPSG3163")

if __name__ == "__main__":
    app = QApplication(sys.argv)
    m = folium.Map(
        location=[45.5236, -122.6750], zoom_start=13
    )

    data = io.BytesIO()
    m.save(data, close_file=False)

    w = QWebEngineView()
    w.setHtml(data.getvalue().decode())
    w.resize(640, 480)
    w.show()

    sys.exit(app.exec_())