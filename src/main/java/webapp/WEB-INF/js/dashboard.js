document.addEventListener('DOMContentLoaded', function() {
    // To be added --> any dashboard-specific JavaScript here
    console.log('Dashboard loaded');
});

function showConfirmDialog(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

function showAlert(message) {
    alert(message);
}