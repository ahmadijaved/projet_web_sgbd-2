/* global isThresholdOfSuccess, capacityId, capacity, params, articles, desc */

import { Capacity } from "./Capacity.js";

var cap;

class Cpacity {
    url = 'CapacityCtrl';

    constructor() {
    }

    doDelete() {
        let id = document.getElementById("capacityId").value;
        let params = "Action=doDelete&id=" + id;
        this.doAsyncCall(params)
                .then(data => this.displayList(data)
                , (error) => {
                    console.log(error.message);
                    document.getElementById("capacity").innerHTML = error.message;
                });
    }

    doNew() {
        document.getElementById("capacityId").value = "";
        document.getElementById("name").value = "";
        document.getElementById("description").value = "";
        document.getElementById("isThresholdOfSuccess").value = "";
        document.getElementById("ue").value = "";
        
    }

    doSave() {
        let id = document.getElementById("capacityId").value;
        let name = document.getElementById("name").value;
        let description = document.getElementById("description").value;
        let ue = document.getElementById("ue").value;
        let isThresholdOfSuccess = document.getElementById("isThresholdOfSuccess");
        let categoryId = e.options[e.selectedIndex].value;
        let params = "Action=doSave&id=" + id + "&name=" + name + "&capacityId=" + capacityId + "&description=" + description +
                "&isThresholdOfSuccess =" + isThresholdOfSuccess +  "&ue=" + ue + ;
        this.doAsyncCall(params)
                .then(data => this.displayList(data)
                , (error) => {
                    console.log(error.message);
                    document.getElementById("capacity").innerHTML = error.message;
                });
    }

    getList() {
        let name = document.getElementById("sName").value;
        let description = document.getElementById("sDescritpion").value;
        let Ue = document.getElementById("sUe\n\
").value;
        let params = "Action=getList&name=" + name + "&capacityId =" + capacityId + &isThresholdOfSuccess =" + isThresholdOfSuccess +  "&ue =" + ue + "&description=" +description;
 
        this.doAsyncCall(params)
                .then(data => this.displayList(data));
    }
    
    getCapacity(capList) {
        let params = "Action=getList";
        let html = "";
        this.doAsyncCall(params)
                .then((capacity) => {                   
                    for (let i = 0; i < capacity.length; i++) {
                        html += "<option ";
                        html += "value='" + capacity[i].capacityid + "'>";
                        html += capacity[i].name + "</option>";
                    }     
                    capList = html;
                    //document.getElementById(selectId).innerHTML = html;
                }, (error) => {
                    console.log('Promise rejected.');
                    console.log(error.message);
                });
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

    getOne(CapacityId) {
        let params = "Action=getOne&capacityId=" + CapacityId;
    
        this.doAsyncCall(params)
                .then((Capacity) => {
                    if (Capacity !== null) {
                        document.getElementById("capacityId").value = capacity.capacityId;
                        document.getElementById("name").value = Capacity.name;
                        document.getElementById("description").value = capacity.description;
                        document.getElementById('ue').value = capacity.ue.;
                    }
                }, (error) => {
                    console.log('Promise rejected.');
                    console.log(error.message);
                });
    }

    displayList(data) {
        console.log("Prepare data to display");
        let capacity = new Object();
        let htmlTable = "<table>";

        capacity = data; // JSON.parse(data);
        for (let i = 0; i < capacity.length; i++) {
            htmlTable += "<tr>";
            htmlTable += "<td class='colCapacityeName'>" + capacity[i].name + "</td>";
            htmlTable += "<td class='colCapcacityDesc'>" + capacity[i].description + "</td>";
            htmlTable += '<td class="colCapacityId">' + '<a onclick="cap.getOne(' + capacity[i].capacityid + ')" >' + capacity[i].capacityid + '</a></td>';
            htmlTable += "</tr>";
        }
        htmlTable += "</table>";
        let lst = document.getElementById("capacity");
        lst.innerHTML = htmlTable;
    }
}

function initCapacity() {
    cap = new Capacity();

    
    let desc = new description();
    desc.getDescription();
    cap.getList();
    
    window.cap = cap;
}

export { Capacity, initCapacity };


