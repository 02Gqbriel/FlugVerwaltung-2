const params = new URLSearchParams(window.location.search);
const requestedObject = params.get("req"),
    uuid = params.get("uuid");

const innerElements = async () => {
    const res = await fetch(`/FlugVerwaltung-2-1.0/api/${requestedObject}/get?uuid=${uuid}`),
        container = document.createElement("div");

    container.className = "flex max-w-xl flex-col mx-auto p-3"

    if (!res.ok) return; // todo error page

    const data = await res.json();
    for (const key of Object.keys(data)) {
        container.appendChild(inputPair(key, data[key]));
    }

    function inputPair(labelText, value = "", inputType = "text") {
        const container = document.createElement("div"),
            input = document.createElement("input"),
            label = document.createElement("label");

        container.className = "flex w-full justify-between m-2 items-center";
        input.className = "rounded"

        container.dataset.key = labelText;

        input.value = value;
        input.name = labelText;
        input.id = labelText;
        input.type = inputType;
        label.innerText = [...labelText].map((e, i) => i == 0 ? e.toUpperCase() : e).toString().replaceAll(",", "") + ":";
        label.htmlFor = labelText;

        container.appendChild(label);
        container.appendChild(input);

        return container;
    }

    const button = document.createElement("button");
    button.innerText = "edit"
    button.className = "bg-blue-500 mt-5 border-2 border-blue-500 hover:border-blue-600 active:bg-blue-400 rounded mx-56 text-white p-1"

    button.onclick = async () => {
        let data = "";

        [...container.children].forEach(
            (e, i) => {
                if (e.children.length > 1) {
                    const value = e.children[1].value;

                    data += e.dataset.key + "=" + value + (i == container.children.length - 2 ? "" : "&")
                }
            }
        )

        const res = await fetch(
            `/FlugVerwaltung-2-1.0/api/${requestedObject}/update`,
            {method: "PUT", body: data, headers: {"Content-Type": "application/x-www-form-urlencoded"}});

        if (!res.ok) {
            return alert("Ups somethin went wrong! Check if format is correct");
        }

        window.location.assign("/FlugVerwaltung-2-1.0")
    }

    container.appendChild(button);

    return container;
}

window.onload = async () => {
    const form = document.querySelector("#form");

    form.appendChild(await innerElements());
}