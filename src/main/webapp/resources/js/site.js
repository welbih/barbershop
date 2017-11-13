$(document).ready(function(){
    habilitarMascaras();
    maskValor();
    habilitarTooltip();
});

function habilitarMascaras() {
    $(".js-celular").mask("(99) 99999-9999");
    $(".js-cpf").mask("999.999.999-99");
    $(".js-data").mask("99/99/9999");
}

function maskValor() {
    $(".js-valor").maskMoney({
        prefix:'R$ ', 
        allowNegative: true, 
        thousands:'.', 
        decimal:',', 
        affixesStay: false});

}
function habilitarTooltip()
{
    $('[data-toggle="tooltip"]').tooltip();
}


