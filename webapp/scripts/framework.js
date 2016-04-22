/*
    Helper functions for the first exercise of the Web Engineering course
*/

/* 
    checks if native form validation is available.
    Source/Further informations: http://diveintohtml5.info/everything.html
*/
function hasFormValidation() {
    return 'noValidate' in document.createElement('form');
}

/* 
    checks if native date input is available.
    Source/Further informations: http://diveintohtml5.info/everything.html
*/
function hasNativeDateInput() {
    var i = document.createElement('input');
    i.setAttribute('type', 'date');
    return i.type !== 'text';
}

var DATE_DELIMITERS = ['/','\\','-'];

/*
    returns the string representation of a date input field in the format dd.mm.yyyy.
    If the value of the input field can't be interpreted as a date, the original value is returned.
*/
function getNormalizedDateString(selector) {
    value = $(selector).val();
    
    // normalize delimiter to .
    for(var i = 0; i < DATE_DELIMITERS.length; i++) 
        value = value.split(DATE_DELIMITERS[i]).join(".");
    
    // check if date might be reverse, i.e., yyyy.mm.dd
    rehtml5 = /^(\d{4})\.(\d{1,2})\.(\d{1,2})$/;
    if(regs = value.match(rehtml5))
        value = regs[3] + "." + regs[2] + "." + regs[1];

    // check if valid date string dd.mm.yyyy
    date = /^(\d{1,2})\.(\d{1,2})\.(\d{4})$/;
    if(value.match(date))
      return value;
    return $(selector).val();
}

/*
    returns the string representation of the given value (seconds) in the format mm:ss.
*/
function secToMMSS(value){
    var minutes = Math.floor(value / 60);
    var seconds = (value % 60);
    
    if(seconds < 10) {
        seconds = "0" + seconds;
    }
    if(minutes < 10) {
        minutes = "0" + minutes;
    }
    return minutes + ":" + seconds;
}

/* 
  checks if native form validation is available.
  Source/Further informations: http://diveintohtml5.info/storage.html
*/
function supportsLocalStorage() {
    try {
        return 'localStorage' in window && window['localStorage'] !== null;
    } catch(e) {
        return false;
    }
}

function writeNewText(el, secs) {
    if (secs > 0) {
        secs--;
        el.html(secToMMSS(secs));
    }
    else {
        el.html(el.data("end-text"));
        el.parents(".product").addClass("expired");
    }
}
$(".js-time-left").each(function() {
    var endTime = $(this).data("end-time").split(",");
    endTime = new Date(endTime[0],endTime[1]-1,endTime[2],endTime[3],endTime[4],endTime[5],endTime[6]);
    var today = new Date();
    var diffS = Math.round((endTime - today) / 1000);
    var that = $(this);
    writeNewText(that, diffS);
    setInterval(function () {
        if (diffS > 0) {
            diffS--;
        }
        writeNewText(that, diffS);
    }, 1000);
});

function formatCurrency(x) {
    // regex from http://stackoverflow.com/a/2901298
    return x.toFixed(2).replace(".", $("body").data('decimal-separator')).replace(/\B(?=(\d{3})+(?!\d))/g, $("body").data('grouping-separator')) + "&nbsp;€";
}

// Depending on the setup of your server, servlet, and socket, you may have to
// change the URL.
var socket = new WebSocket("ws://localhost:8080/socket");
socket.onmessage = function (event) {
    /***  write your code here ***/
    

    var mess = JSON.parse(event.data);

    switch(mess.type) {
        case "expiredAuction":
            alert("auction expired");
            //This isn't executed, maybe because the selector isn't correct
            //But then i have no plan how to get dynamically created forms
            //(document).getElementById(mess.productId).addClass("expired");

            endtime = $(".product-time");
            for (var i = 0; i < endtime.length; i++) {
                endtime[i].setAttribute("data-end-text", "abgelaufen");
                endtime[i].html = "abgelaufen";
                endtime[i].style.display = "block";
            }

            //-----------All the below are executed-----------------------

            runningAuctions = $(".running-auctions-count");
            for (var i = 0; i < runningAuctions.length; i++) {
                runningAuctions[i].innerHTML = mess.runningA;
            }
            balance = $(".balance");
            for (var i = 0; i < runningAuctions.length; i++) {
                balance[i].innerHTML = mess.budget;
            }
            wonAuctions = $(".won-auctions-count");
            for (var i = 0; i < wonAuctions.length; i++) {
                wonAuctions[i].innerHTML = mess.wonA;
            }
            lostAuctions = $(".lost-auctions-count");
            for (var i = 0; i < lostAuctions.length; i++) {
                lostAuctions[i].innerHTML = mess.lostA;
            }
            detailTime = $(".detail-time");
            for (var i = 0; i < detailTime.length; i++) {
                detailTime[i].style.display = "none";
            }
            
            //isn't executed
            (document).getElementById("bid-form").style.display = "none";

            //-------------------------------------------------------------

            //This isn't executed, i don't know why
            //document.getElementById("exptext").style.display = "block";
            break;
        
        case "newbid":
            // changes the budget of the old maxbidder in overview and detail and notifies him
            if ($("#user-name").html() == mess.oldmaxbidder) {
                // changes the display
                $("#detailBudget").html(mess.budForOld);
                $("#overviewBudget").html(mess.budForOld);

                // notifies the old maxbidder
                window.alert("Ihr Gebot beim Produkt " + mess.pid + " wurde ueberboten. Ihr Kontostand betraegt nun "
                    + mess.budForOld + " EUR.")
            }


            // var is Message for all for a new bid
            var stringmessageForAll = "Der Benutzer " + mess.currentUser + " hat fuer das Produkt mit der ID " + mess.pid + " den Betrag "
                + mess.newProductPrice + " EUR geboten!";

            // update the new price and bidder of detail site
            if ($("#detailID").val() == mess.pid) {
                $("#maxBdetail").html(mess.currentUser);
                $("#newPdetail").html(mess.newProductPrice);
            }


            // update the new price and bidder of overview site
            var maxBidder = "#maxbidderOver" + mess.pid;
            var price = "#priceOver" + mess.pid;


            if ($(maxBidder) != null) {
                $(maxBidder).html(mess.currentUser);
            }

            if ($(price) != null) {
                $(price).html(mess.newProductPrice + " &#8364");
            }


            // notify all users of the new bid/bidder/price of a certain product
            window.alert(stringmessageForAll);
            break;
    }
    
};


