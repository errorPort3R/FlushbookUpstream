var map;
function initMap() {
  var myLatLng = {lat: 32.7765, lng: -79.9311};

  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 32.7765, lng: -79.9311},
    zoom: 16
  });
}

function newMarker(item) {
  var marker = new google.maps.Marker({
    position: {lat:  Number(item.lat), lng: Number(item.lon)},
    map: window.map,
    title: item.facility
  });
}

function codeAddress() {
    var address = document.getElementById("address").value;
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location);
        console.log(results[0].geometry.location);
        var marker = new google.maps.Marker({
            map: map,
            position: results[0].geometry.location
        });
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
  }
