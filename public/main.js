// to test on another server;
// copy paste http://tiny-tiny.herokuapp.com/collections/flushbook into urls
// on line 37 replace: mainPage.create(JSON.stringify((restroom));
//            with : mainPage.create(restroom)
// on line 73 comment out data = JSON.parse(data);
// also on line 78 try uncommenting the new marker function. this function is what will put the marek on the map




$(document).ready(function() {
    mainPage.init();

})
// api key: AIzaSyCmljv68nIytDeweNCQXnOGt7_Z3Rz9Neo
var mainPage = {
    restroom: [],
    // don't forget The commas!!!!!!!!
    init(){
      mainPage.styling();
      mainPage.events();
      setInterval(function() {
        mainPage.read();
      },2000);
    },
    styling(){
      mainPage.read();
    },

    //end of styling
    events(){
      $('form button').on('click', function(event){
        event.preventDefault();
        // codeAddress();
        var restroom = {
          facility:$('input[name="facility"]').val(),
          address: $('input[name="address"]').val(),
          lat: $('input[name="lat"]').val(),
          lon: $('input[name="lon"]').val(),
          access: $('input[name="access"]').val(),
          capacity: $('input[name="capacity"]').val(),
          cleanliness: 0,
        }
        console.log(restroom);
        mainPage.create(restroom);
        mainPage.read();
      })

    $('.locationTracker ul').on('click', 'li button[name=Delete]', function(event){
      event.preventDefault();
      console.log($(this));
      // console.log($(this).parent);
      var deleteId = $(this).parent().data('id');
      console.log(deleteId);
      mainPage.destroy(deleteId);
    })
  },
    //end of events

    //crud ajax functions
    create(restroomObject){
        $.post({
            contentType: "application/json; charset=utf-8",
            url:"/flush",
            method: "POST",
            data: JSON.stringify(restroomObject),
            success(data) {
                console.log("created", data);
            },
            error(err) {
                console.error("you made nothing", err);
            },
        })
    },
    //end of create

    read() {
        $.ajax({
            url:"/flush",
            method: "GET",

            success(data) {
                console.log("we got it", data);
                $('.locationTracker ul').html("");

                data = JSON.parse(data);
                data.reverse();
                data.forEach(function(item) {
          $('.locationTracker ul').append(`<li data-id=${item.id}>${item.facility} ${item.lat} ${item.lon}  ${item.capacity} ${item.cleanliness} <button type="button" name="update">Update</button><button type="button" name="Delete">Delete</button></li>`);
            newMarker(item);
        })
      },
            error(err){
                console.error("shit", err);
            },

    })
  },
// end of read

    update(updateId){
        $.ajax({
            url:`/flush/`+updateId,
            method: "PUT",

            success(data) {
                console.log("update success", data);
            },
            error(err) {
                console.error("dammit", err);
            },
        })
    },
//end of update
// var deleteId = mainPage.url + "/" + chatId;

    destroy(deleteId) {
        $.ajax({
            url:"/flush/"+deleteId,
            method: "DELETE",

            success: function(data) {
              console.log("WE DELETED SOMETHING", data);
              mainPage.read();
            },
            error: function(err) {
              console.error("OH CRAP", err);
            }
          })
        }
      }
// deleteChat: function(chatId) {
//   // find blog to delete from our blog data;
//   var deleteId = mainPage.url + "/" + chatId;
//   $.ajax({
//     url: deleteId,
//     method: "DELETE",
//     success: function(data) {
//       console.log("WE DELETED SOMETHING", data);
//       mainPage.read();
//     },
//     error: function(err) {
//       console.error("OH CRAP", err);
//     }
//   })
// },
