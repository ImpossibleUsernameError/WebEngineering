/**
 * Created by Marlene on 18.04.2016.
 */

function printProductOfStorageOverview() {
    var oldO=document.getElementById("lastSeenListOverview");
    prepareElementsForPrintProduct(oldO);
}

function printProductOfStorageDetails() {
    var oldO=document.getElementById("lastSeenListDetails");
    prepareElementsForPrintProduct(oldO);
}

function prepareElementsForPrintProduct(parent){
    for(i = 0; i<sessionStorage.length;i++){
        var key = sessionStorage.key(i);
        var name = sessionStorage.getItem(key);

        var form = document.createElement("form");
        form.action = "DetailServlet?product=<" + key;
        form.method = "GET";

        var newO=document.createElement("a");
        newO.href = "DetailServlet?product="+key;
        newO.innerHTML = name;
        
        var list = document.createElement("li");
        if(!supportsLocalStorage()){
            list.className = "recently-viewed-list";
        }

        list.appendChild(newO);
        form.appendChild(list);
        parent.appendChild(form);
    }
}
