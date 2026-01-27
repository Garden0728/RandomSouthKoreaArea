let lastData = null;
let loadingMessageTimer = null;
const loadingMessages = [
    "지역을 가져오는 중...",
    "열심히 뽑아오는 중...",
    "지도를 준비하는 중..."
];

function setLoading(isLoading) {
    const loading = document.getElementById("loading");
    const loadingText = document.getElementById("loading-text");
    const button = document.getElementById("random-btn");
    if (loading) {
        loading.style.display = isLoading ? "flex" : "none";
    }
    if (button) {
        button.disabled = isLoading;
        button.textContent = isLoading ? "뽑는 중..." : "랜덤 뽑기";
    }
    if (loadingText) {
        loadingText.textContent = loadingMessages[0];
    }
    if (isLoading) {
        let index = 0;
        loadingMessageTimer = setInterval(() => {
            index = (index + 1) % loadingMessages.length;
            if (loadingText) {
                loadingText.textContent = loadingMessages[index];
            }
        }, 1000);
    } else if (loadingMessageTimer) {
        clearInterval(loadingMessageTimer);
        loadingMessageTimer = null;
    }
}

function updateResultBoxes(data) {
    const resultBox = document.getElementById("result-box");
    const mapResultBox = document.getElementById("map-result-box");
    const html = `
        <div><b>X:</b> ${data.x}</div>
        <div><b>Y:</b> ${data.y}</div>
        <div><b>주소:</b> ${data.address.address_name}</div>
    `;
    if (resultBox) {
        resultBox.innerHTML = html;
    }
    if (mapResultBox) {
        mapResultBox.innerHTML = html;
    }
}

function getSelectedRegions() {
    return Array.from(
        document.querySelectorAll('input[name="region"]:checked')
    ).map(input => input.value);
}

async function handleRandom() {
    const regions = getSelectedRegions();
    const showMapBtn = document.getElementById("show-map-btn");
    if (showMapBtn) {
        showMapBtn.style.display = "none";
    }
    setLoading(true);
    try {
        const dataPromise = window.Api.fetchRandomCoordinate(regions);
        const minDelay = new Promise(resolve => setTimeout(resolve, 3000));
        const [data] = await Promise.all([dataPromise, minDelay]);
        lastData = data;
        updateResultBoxes(data);
        if (showMapBtn) {
            showMapBtn.style.display = "block";
        }
    } finally {
        setLoading(false);
    }
}

function showMapView() {
    const content = document.getElementById("content");
    if (content) {
        content.classList.add("map-view");
        content.classList.remove("map-hidden");
    }
    window.MapView.ensureMap();
    window.MapView.relayout();
    window.MapView.renderFromData(lastData);
}

function showConditionView() {
    const content = document.getElementById("content");
    if (content) {
        content.classList.remove("map-view");
        content.classList.add("map-hidden");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const content = document.getElementById("content");
    if (content) {
        content.classList.add("map-hidden");
        content.classList.remove("map-view");
    }
    const randomBtn = document.getElementById("random-btn");
    if (randomBtn) {
        randomBtn.addEventListener("click", handleRandom);
    }
    const showMapBtn = document.getElementById("show-map-btn");
    if (showMapBtn) {
        showMapBtn.style.display = "none";
        showMapBtn.addEventListener("click", showMapView);
    }
    const backBtn = document.getElementById("back-to-conditions");
    if (backBtn) {
        backBtn.addEventListener("click", showConditionView);
    }
});
