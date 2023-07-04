
var org;

class Organized {
    url = 'OrganizedCtrl';

    constructor() {
    }

    doDelete() {
        let id = document.getElementById("organizedid").value;
        let params = "Action=doDelete&id=" + id;
        this.doAsyncCall(params)
                .then(data => {
                    this.displayList(data);
                    document.getElementById("organizedid").value = "";
                    document.getElementById("name").value = "";
                }
                , (error) => {
                    console.log(error.message);
                    document.getElementById("organized").innerHTML = error.message;
                });
    }

    doNew() {
        document.getElementById("organizedid").value = "";
        document.getElementById("name").value = "";
    }

    doSave() {
        let id = document.getElementById("organizedid").value;
        let name = document.getElementById("name").value;

        let params = "Action=doSave&id=" + id + "&name=" + name;
        this.doAsyncCall(params)
                .then(data => this.displayList(data)
                , (error) => {
                    console.log(error.message);
                    document.getElementById("organized").innerHTML = error.message;
                });
    }

    getList() {
        let name = document.getElementById("sName").value;
        let params = "Action=getList&name=" + name;

        this.doAsyncCall(params)
                .then(data => this.displayList(data));
    }

    async doAsyncCall(params)
    {
        let response = await fetch(this.url, {
            method: 'post',
            body: params,
            headers: {'Content-type': 'application/x-www-form-urlencoded'}
        });
        let data = await response.json();
        return data;
    }

    getOrganized() {
        let params = "Action=getList";

        this.doAsyncCall(params)
                .then((organized) => {
                    let html = "";
                    for (let i = 0; i < organized.length; i++) {
                        html += "<option ";
                        html += "value='" + organized[i].organizedId + "'>";
                        html += organized[i].name + "</option>";
                    }
                    document.getElementById("organized").innerHTML = html;
                }, (error) => {
                    console.log('Promise rejected.');
                    console.log(error.message);
                });
    }

    getOne(OrganizedId) {
        let params = "Action=getOne&organizedId=" + OrganizedId;

        this.doAsyncCall(params)
                .then((category) => {
                    if (category !== null) {
                        document.getElementById("organizedId").value = Organized.organizedId;
                        document.getElementById("name").value = category.name;
                    }
                }, (error) => {
                    console.log('Promise rejected.');
                    console.log(error.message);
                });
    }

    displayList(data) {
        console.log("Prepare data to display");
        let Organized = new Object();
        let htmlTable = "<table>";

        Organized = data;
        for (let i = 0; i < Organized.length; i++) {
            htmlTable += "<tr>";
            htmlTable += "<td class='colOganizedUeId'>" + Organized[i].id + "</td>";
            htmlTable += '<td class="colArticleId">' + '<a onclick="cat.getOne(' + categories[i].categoryId + ')" >' + categories[i].categoryId + '</a></td>';
            htmlTable += "</tr>";
        }
        htmlTable += "</table>";
        let lst = document.getElementById("categories");
        lst.innerHTML = htmlTable;
    }
}

function initPage() {

    cat = new Category();
    window.cat = cat;
    cat.getList();
}

export { Category, initPage};


