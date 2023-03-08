function displayJson(json, div) {
    for (const key of Object.keys(json)) {
        if (key == "Ratings") {
            div.innerHTML += "Ratings: "
            for (const ratingKey of Object.keys(json[key])) {
                div.innerHTML += json[key][ratingKey]["Source"] + ": " + json[key][ratingKey]["Value"] + ", ";
            }
            div.innerHTML += "<br/>"
        } else {
            div.innerHTML += key + ": " + json[key] + "<br/>";
        }
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
    } /*document.getElementById("postrespmsg").innerHTML = data*/);
}

function sendGetMsg() {
    let url = "/logs";
        fetch (url, {method: 'GET'})
            .then(response => response.text())
            .then(data => {
                let div = document.getElementById("getrespmsg");
                div.innerHTML = "";
                console.log(data);
                div.innerHTML = data;
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