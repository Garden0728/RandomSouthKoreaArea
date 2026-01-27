const MapState = {
    map: null,
    marker: null,
    polygon: null
};

function createMap() {
    MapState.map = new kakao.maps.Map(document.getElementById('map'), {
        center: new kakao.maps.LatLng(36.3504, 127.3845),
        level: 12
    });
    // 대한민국 육지가 보이도록 초기 범위 설정
    const bounds = new kakao.maps.LatLngBounds(
        new kakao.maps.LatLng(33.0, 124.5), // 남서
        new kakao.maps.LatLng(38.6, 131.9)  // 북동
    );
    MapState.map.setBounds(bounds);
    MapState.map.setMaxLevel(12);
}

function ensureMap() {
    if (!MapState.map && document.getElementById("map")) {
        createMap();
    }
}

function removeMarker() {
    if (MapState.marker) {
        MapState.marker.setMap(null);
        MapState.marker = null;
    }
}

function removePolygon() {
    if (MapState.polygon) {
        MapState.polygon.setMap(null);
        MapState.polygon = null;
    }
}

function createMarker(x, y) {
    removeMarker();
    const markerPosition = new kakao.maps.LatLng(y, x);

    MapState.marker = new kakao.maps.Marker({
        map: MapState.map,
        position: markerPosition
    });
    // 카카오가 제공하는 좌표를 중심으로 지도를 이동
    MapState.map.panTo(markerPosition);
}

function createPolygon(polygon) {
    removePolygon();
    const path = polygon.map(p => new kakao.maps.LatLng(p[1], p[0]));
    MapState.polygon = new kakao.maps.Polygon({
        map: MapState.map,
        path: path, // 그려질 다각형의 좌표 배열
        strokeWeight: 3.5,// 선의 두께
        strokeColor: '#004c80',//// 선의 색상
        strokeOpacity: 0.7, // 선의 불투명도 (1 ~ 0)
        strokeStyle: 'longdash', // 선의 스타일
        fillColor: '#fff', // 채우기 색상
        fillOpacity: 0.5 //// 채우기 불투명도
    });
    MapState.polygon.setMap(MapState.map);
}

function renderFromData(data) {
    if (!data || !MapState.map) {
        return;
    }
    createMarker(parseFloat(data.x), parseFloat(data.y));
    createPolygon(data.polygonCoordinates);
}

function relayout() {
    if (MapState.map) {
        MapState.map.relayout();
    }
}

window.MapView = {
    ensureMap,
    renderFromData,
    relayout
};
