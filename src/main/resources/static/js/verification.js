function setVerificationStatus(message, isError = false) {
    const status = document.getElementById("verification-status");
    if (status) {
        status.textContent = message || "";
        status.classList.toggle("error", isError);
    }
}

function setSubmitLoading(isLoading) {
    const button = document.getElementById("verification-submit-btn");
    if (button) {
        button.disabled = isLoading;
        button.textContent = isLoading ? "업로드 중..." : "업로드";
    }
}

async function submitVerification(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    setSubmitLoading(true);
    setVerificationStatus("업로드를 진행 중입니다...", false);
    try {
        const response = await fetch("/api/v1/verification/submit", {
            method: "POST",
            body: formData
        });
        const data = await response.json();
        if (!response.ok) {
            setVerificationStatus(data.message || "업로드에 실패했습니다.", true);
            return;
        }
        setVerificationStatus(data.message || "업로드가 완료되었습니다.", false);
        form.reset();
    } catch (error) {
        setVerificationStatus("네트워크 오류가 발생했습니다. 다시 시도해주세요.", true);
    } finally {
        setSubmitLoading(false);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("verification-form");
    if (form) {
        form.addEventListener("submit", submitVerification);
    }
});
