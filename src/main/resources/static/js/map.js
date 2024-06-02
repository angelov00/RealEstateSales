const geojsonPath = '/js/provinces.geojson';

fetch(geojsonPath)
    .then(response => {

        if (!response.ok) {
            throw new Error(`Failed to fetch GeoJSON file (status ${response.status})`);
        }

        return response.json();
    })
    .then(geojsonData => {

        const geoJson = geojsonData;
        let southWest = L.latLng(41.2354, 22.3597);
        let northEast = L.latLng(44.2148, 28.6070);
        let bounds = L.latLngBounds(southWest, northEast);

        let mapDiv = document.getElementById("map");

        let map = L.map(mapDiv, {
            center: [42.7000, 25.4858],
            zoom: 7.45,
            maxBounds: bounds,
            dragging: true
        });

        let provincesLayer = L.geoJSON(geoJson, {
            onEachFeature: function (feature, layer) {
                layer.on('mouseover', function (e) {
                    // Change color on hover
                    layer.setStyle({
                        fillColor: 'yellow',
                        fillOpacity: 0.7
                    });
                    console.log(feature.properties.nuts3);
                });

                layer.on('mouseout', function (e) {
                    // Reset color on mouseout
                    layer.setStyle({
                        fillColor: '#BBCCE4', // Change this to the original color
                        fillOpacity: 0.5 // Change this to the original opacity
                    });
                });

                layer.on('click', function (e) {
                    document.getElementById("province").setAttribute('value', feature.properties.nuts3);
                });
            }
        }).addTo(map);

        map.setMaxBounds(bounds);

    })
    .catch(error => {
        console.error('Error fetching GeoJSON file:', error);
    });

