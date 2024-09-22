document.getElementById('analyze-btn').addEventListener('click', function() {
    const month = document.getElementById('month').value;
    fetch(`/travel/analyze?month=${month}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#results-table tbody');
            tableBody.innerHTML = '';
            data.forEach(row => {
                const tr = document.createElement('tr');
                tr.innerHTML = `<td>${row.region}</td><td>${row.bestTime}</td><td>${row.price}</td>`;
                tableBody.appendChild(tr);
            });
        });
});