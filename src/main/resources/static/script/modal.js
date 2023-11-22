
function testModal (){
    var swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "modal_button modal_button_confirm",
            denyButton: "modal_button modal_button_deny",
            cancelButton: "modal_button modal_button_cancel"
        },
        buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        showDenyButton: true,
        confirmButtonText: "Yes, delete it!",
        denyButtonText: "wow!",
        cancelButtonText: "No, cancel!",
        reverseButtons: false
    }).then((result) => {
        console.log(result);
        if (result.isConfirmed) {
            swalWithBootstrapButtons.fire({
                title: "Deleted!",
                text: "Your file has been deleted.",
                icon: "success"
            });
        } else if (result.isDenied) {
            swalWithBootstrapButtons.fire({
                title: "Deleted!2",
                text: "Your file has been deleted.",
                icon: "success"
            });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire({
                title: "Cancelled",
                text: "Your imaginary file is safe :)",
                icon: "error"
            });
        }
    });
}