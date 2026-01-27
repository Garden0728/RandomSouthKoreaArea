async function fetchRandomCoordinate(regions) {
    const params = new URLSearchParams();
    if (regions && regions.length > 0) {
        regions.forEach(region => params.append("region", region));
    }
    const query = params.toString() ? `?${params.toString()}` : "";
    const response = await fetch(`/api/v1/Random_coordinate-Address/create${query}`);
    return await response.json();
}

window.Api = {
    fetchRandomCoordinate
};
