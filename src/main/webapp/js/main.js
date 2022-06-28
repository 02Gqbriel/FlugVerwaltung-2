const data = {airplanes: [], airports: [], flights: []},
    urls = ["/FlugVerwaltung-2-1.0/api/airplane/list", "/FlugVerwaltung-2-1.0/api/airport/list", "/FlugVerwaltung-2-1.0/api/flight/list"];

var done = false;

(async () => {
    const keyArrays = Object.keys(data);

    for (let i = 0; i < keyArrays.length; i++) {
        const res = await fetch(urls[i] + "");
        if (!res.ok) continue;
        data[keyArrays[i]] = await res.json();
    }

    done = true;
})();

window.onload = async () => {
    const root = document.querySelector("#root");

    (function waitForData() {
        if (!done) {
            setTimeout(waitForData, 100);
        } else {
            visualizeData();
        }
    })();

    function visualizeData() {
        const keyArrays = Object.keys(data),
            createTr = () => document.createElement("tr");


        for (let i = 0; i < keyArrays.length; i++) {
            if (data[keyArrays[i]].length == 0) continue;
            const title = document.createElement("h3"),
                table = document.createElement("table"),
                div = document.createElement("div");

            table.className = "border border-black"

            title.textContent = keyArrays[i].replace(keyArrays[i].charAt(0), keyArrays[i].charAt(0).toUpperCase());
            title.className = "text-xl mb-1"

            for (let j = 0; j < data[keyArrays[i]].length; j++) {

                let item = data[keyArrays[i]][j],
                    keys = Object.keys(item);

                if (j == 0) {
                    const tr = createTr();

                    for (let k = 0; k < keys.length + 2; k++) {
                        const th = document.createElement("th");

                        if (keys[k] != undefined && keys[k].includes("UUID")) continue;

                        th.innerText = keys[k] || "";
                        th.className = "border border-black p-1 text-left text-sm"

                        tr.appendChild(th);
                    }

                    table.appendChild(tr);

                }

                const tr = createTr();

                for (let k = 0; k < keys.length + 2; k++) {
                    const tb = document.createElement("td");

                    if (keys[k] != undefined && keys[k].includes("UUID")) {
                        tr.dataset.uuid = item[keys[k]];
                        tr.dataset.class = keyArrays[i];
                        continue;
                    }

                    tb.innerHTML = item[keys[k]] || (k === keys.length ? editSVG : deleteSVG);
                    tb.className = "border border-black p-1 font-light text-sm"

                    tr.appendChild(tb);
                }

                table.appendChild(tr);
            }
            div.appendChild(title);
            div.appendChild(table);

            const hr = document.createElement("hr");

            root.appendChild(div);
            root.appendChild(hr);
        }

        console.log(data);

        const editButtons = document.querySelectorAll(".editSvg"),
            deleteButtons = document.querySelectorAll(".deleteSvg");

        editButtons.forEach((e, i) => {
            const parent = e.parentElement.parentElement;

            e.onclick = async () => {
                window.location.assign(`/FlugVerwaltung-2-1.0/edit.html?uuid=${parent.dataset.uuid}&req=${parent.dataset.class.slice(0, parent.dataset.class.length - 1)}`)
            };
        });

        deleteButtons.forEach((e, i) => {
            const parent = e.parentElement.parentElement;

            e.onclick = async () => {
                await fetch(`/FlugVerwaltung-2-1.0/api/${parent.dataset.class.slice(0, parent.dataset.class.length - 1)}/delete?uuid=${parent.dataset.uuid}`, {method: "DELETE"});
                window.location.reload();
            };
        });
    }
}

const editSVG = '<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 hover:bg-slate-200 rounded p-1 editSvg" viewBox="0 0 20 20" fill="currentColor">\n' +
    '  <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z" />\n' +
    '  <path fill-rule="evenodd" d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z" clip-rule="evenodd" />\n' +
    '</svg>';

const deleteSVG = '<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 hover:bg-red-300 rounded p-1 deleteSvg" viewBox="0 0 20 20" fill="currentColor">\n' +
    '  <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd" />\n' +
    '</svg>'