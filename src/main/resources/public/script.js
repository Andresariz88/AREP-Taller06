function displayJson(json, div) {
    for (const key of Object.keys(Object.keys(json))) {
        div.innerHTML += key + ": " + JSON.stringify(json[key].value) + " - " + JSON.stringify(json[key].createdAt) + "<br/>";
    }
}

function sendPostMsg(name){
    let url = "/logs";
    fetch (url, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify ({
            value: name.value
        })
    })
        .then(response => response.text())
        .then(data => {
            let div = document.getElementById("getrespmsg");
            div.innerHTML = "";
            console.log(data);
            div.innerHTML = data;
    });
}

function sendGetMsg() {
    let url = "/logs";
        fetch (url, {method: 'GET'})
            .then(response => response.json())
            .then(data => {
                let div = document.getElementById("getrespmsg");
                div.innerHTML = "Últimos 10 logs: <br/>";
                console.log(data);
                displayJson(data, div);
        });
}

function loadMsg(name) {
    console.log('name.value :>> ', name.value);
    
    method = name.value == "" ? 'GET' : 'POST';
    console.log('method :>> ', method);

    if (method == 'POST') {
        sendPostMsg(name);
    } else {
        sendGetMsg();
    }

    
}