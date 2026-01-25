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
    // 대한민국 육지가 보이도록 초기 범위 설정
    const bounds = new kakao.maps.LatLngBounds(
        new kakao.maps.LatLng(33.0, 124.5), // 남서
        new kakao.maps.LatLng(38.6, 131.9)  // 북동
    );
    MapSate.map.setBounds(bounds);
    MapSate.map.setMaxLevel(12);

}

function createMarker(x, y) {
    removeMarker()
    let markerPosition = new kakao.maps.LatLng(y, x);

    MapSate.marker = new kakao.maps.Marker({
        map: MapSate.map,
        position: markerPosition
    });
    // 카카오가 제공하는 좌표를 중심으로 지도를 이동
    MapSate.map.panTo(markerPosition);
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
    createMarker(parseFloat(data.x), parseFloat(data.y));
    createPolygon(data.polygonCoordinates);
    updateResultBox(data);
}
function updateResultBox(data){
    let box = document.getElementById("result-box");
    box.innerHTML = `
        <div><b>X:</b> ${data.x}</div>
        <div><b>Y:</b> ${data.y}</div>
        <div><b>주소:</b> ${data.address.address_name}</div>
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
        path: path, // 그려질 다각형의 좌표 배열
        strokeWeight: 3.5,// 선의 두께
        strokeColor: '#004c80',//// 선의 색상
        strokeOpacity: 0.7, // 선의 불투명도 (1 ~ 0)
        strokeStyle: 'longdash', // 선의 스타일
        fillColor: '#fff', // 채우기 색상
        fillOpacity: 0.5 //// 채우기 불투명도
    });
    MapSate.polygon.setMap(MapSate.map);
}

document.addEventListener("DOMContentLoaded", () => {
    // 지도 컨테이너가 있는 페이지에서만 초기화
    if (document.getElementById("map")) {
        createMap();
    }
});

// 전역에서 호출 가능하도록 함수 등록
window.areaLandom = areaLandom;
