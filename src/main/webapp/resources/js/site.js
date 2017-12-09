$(document).ready(function(){
    habilitarMascaras();
    maskValor();
    deabilitarSubmitButton();
});

function habilitarMascaras() {
    $(".js-celular").mask("(99) 99999-9999");
    $(".js-cpf").mask("999.999.999-99");
    $(".js-data").mask("99/99/9999");
    $(".js-quantidade").mask("999");
}

function maskValor() {
    $(".js-valor").maskMoney({
        prefix:'R$ ', 
        allowNegative: true, 
        thousands:'.', 
        decimal:',', 
        affixesStay: false});

}
function deabilitarSubmitButton()
{
    $('input').keypress(function (e) {
         var code = null;
         code = (e.keyCode ? e.keyCode : e.which);                
         return (code == 13) ? false : true;
    });
}


