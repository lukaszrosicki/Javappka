// Prosty skrypt obslugujacy formularze na stronach

function on(id, type, handler) {
    const el = document.getElementById(id);
    if (el) el.addEventListener(type, handler);
}


// rejestracja
on('registerForm', 'submit', (e) => {
    e.preventDefault();
    fetch('/api/users', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email: document.getElementById('regEmail').value, password: document.getElementById('regPassword').value})
    }).then(() => {
        document.getElementById('register-msg').textContent = 'Utworzono konto';
        document.getElementById('register-msg').style.display = 'block';
    });
});

// zmiana hasla i usuwanie konta
on('passwordForm', 'submit', (e) => {
    e.preventDefault();
    const id = document.getElementById('accId').value;
    const pass = document.getElementById('newPassword').value;
    fetch(`/api/users/${id}/password`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(pass)
    }).then(() => {
        document.getElementById('account-msg').textContent = 'Haslo zmienione';
        document.getElementById('account-msg').style.display = 'block';
    });
});

const delBtn = document.getElementById('deleteAccount');
if (delBtn) {
    delBtn.addEventListener('click', () => {
        const id = document.getElementById('accId').value;
        fetch(`/api/users/${id}`, {method: 'DELETE'}).then(() => location.href = '/');
    });
}

// lista wydatkow
function loadExpenses() {
    fetch('/api/expenses').then(r => r.json()).then(data => {
        const list = document.getElementById('expenseList');
        if (!list) return;
        list.innerHTML = '';
        data.forEach(e => {
            const li = document.createElement('li');
            li.className = 'list-group-item';
            li.textContent = `${e.date} - ${e.category} - ${e.amount}`;
            list.appendChild(li);
        });
    });
}
loadExpenses();

on('expenseForm', 'submit', (e) => {
    e.preventDefault();
    fetch('/api/expenses', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            category: document.getElementById('expenseCategory').value,
            amount: document.getElementById('expenseAmount').value,
            date: document.getElementById('expenseDate').value
        })
    }).then(loadExpenses);
});

// lista dochodow
function loadIncomes() {
    fetch('/api/incomes').then(r => r.json()).then(data => {
        const list = document.getElementById('incomeList');
        if (!list) return;
        list.innerHTML = '';
        data.forEach(i => {
            const li = document.createElement('li');
            li.className = 'list-group-item';
            li.textContent = `${i.date} - ${i.category} - ${i.amount}`;
            list.appendChild(li);
        });
    });
}
loadIncomes();

on('incomeForm', 'submit', (e) => {
    e.preventDefault();
    fetch('/api/incomes', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            category: document.getElementById('incomeCategory').value,
            amount: document.getElementById('incomeAmount').value,
            date: document.getElementById('incomeDate').value
        })
    }).then(loadIncomes);
});

// podsumowanie
on('summaryForm', 'submit', (e) => {
    e.preventDefault();
    const y = document.getElementById('sumYear').value;
    const m = document.getElementById('sumMonth').value;
    Promise.all([
        fetch(`/api/incomes/summary?year=${y}&month=${m}`).then(r=>r.json()),
        fetch(`/api/expenses/summary?year=${y}&month=${m}`).then(r=>r.json())
    ]).then(([inc, exp]) => {
        document.getElementById('summaryResult').textContent = `Dochody: ${inc}  Wydatki: ${exp}`;
    });
});
