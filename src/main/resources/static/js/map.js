const MapSate = {
     map : null,
     marker : null,
     polygon : null,
}

function createMap() {
    MapSate.map = new kakao.maps.Map(document.getElementById('map'), {
        center: new kakao.maps.LatLng(36.3504, 127.3845),
        level: 12
    });
    MapSate.map.setMaxLevel(12);

}

function createMarker(x, y) {
    removeMarker()
    let markerPosition = new kakao.maps.LatLng(y, x);

    MapSate.marker = new kakao.maps.Marker({
        map: MapSate.map,
        position: markerPosition
    });
    MapSate.map.panTo(markerPosition); //kakao에서 제공하는 좌표 중심으로 지도 이동시키는 함수
}

function removeMarker() {
    if (MapSate.marker) {
        MapSate.marker.setMap(null);
        MapSate.marker = null;
    }
}

async function getRandomCoordinate() {
    const response = await fetch("/api/v1/Random_coordinate-Address/create")
    return await response.json();
}

async function areaLandom() {
    const data = await getRandomCoordinate();
    createMarker(parseFloat(data.x), parseFloat(data.y), data.address);
    createPolygon(data.polygonCoordinates);
    updateResultBox(data);
}
function updateResultBox(data){
    const box = document.getElementById("result-box");
    box.innerHTML = `
        <div><b>X:</b> ${data.x}</div>
        <div><b>Y:</b> ${data.y}</div>
        <div><br> ${data.address.address_name}</div>
    `;
}

function removePolygon() {
    if (MapSate.polygon) {
        MapSate.polygon.setMap(null);
        MapSate.polygon = null;
    }
}

function createPolygon(Polygon) {
    removePolygon()
    const path = Polygon.map(p => new kakao.maps.LatLng(p[1], p[0]));
    MapSate.polygon = new kakao.maps.Polygon({
        map: MapSate.map,
        path: path, // 그려질 다각형의 좌표 배열입니다.
        strokeWeight: 3.5,//선의 두께입니다
        strokeColor: '#004c80',//// 선의 색깔입니다
        strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'longdash', // 선의 스타일입니다
        fillColor: '#fff', // 채우기 색깔입니다
        fillOpacity: 0.5 //// 채우기 불투명도 입니다
    });
    MapSate.polygon.setMap(MapSate.map);
}

document.addEventListener("DOMContentLoaded", createMap);//모든 html이 만들어진 후 createmap실행

window.areaLandom = areaLandom; //전역 객체인 window로 함수를 전역으로 등록
