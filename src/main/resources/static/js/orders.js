listOder = [];
productOrder = null;

dataAjax = null

ingredients = null;

$(document).ready(function () {
	renderListOrder(listOder);
	getListIngredient();
	console.log(ingredients)
	$('#addToOrder').click(function(){
		quantity = $('#quantity-product').val()*1;
		productOrder.quantity = quantity;
		
		size = $('#size-product').val();
		
		idxSize = dataAjax.productSizes.findIndex(ele => ele.size == size);
		sizeId = dataAjax.productSizes[idxSize].id;
		priceSize = dataAjax.productSizes[idxSize].price;
		
		syntaxSize = dataAjax.productSizes[idxSize].syntaxs
		
		productOrder = { id: sizeId, name: dataAjax.name, size: size, quantity: quantity, price: priceSize, idPro: dataAjax.id, syntax: syntaxSize}
		
		idx = getIndexOrderById(productOrder.id);
		if (idx == -1){
			if (checkQuantityInSyntax(productOrder.id, quantity)){
				listOder.push(productOrder);
			}else {
				$('#dialogError').modal('show')
			}
		} else {
			if (checkQuantityInSyntax(productOrder.id, productOrder.quantity)){
				listOder[idx].quantity += productOrder.quantity;
			}else {
				$('#dialogError').modal('show')
			}
		}
		$("#totalPrice").text(calTotalPrice());
		renderListOrder(listOder);
		$('#ModalAdd').modal('hide')
	})
});

function getListIngredient(){
	$.ajax({
	    url: "http://localhost:8080/ingredients/getlistingredient",
	    type: "GET",
	    dataType: "json",
	    success: function(result) {
			ingredients = result;
	    },
	    
	    error: function(error) {
			console.log(error)
	    }
	});
}

function checkQuantityInSyntax(sizeId, quantityNew){
	listIngredients = [...ingredients]
	for (i = 0; i < listOder.length; ++i){
		syn = listOder[i].syntax;
		quantity = listOder[i].quantity;
		for(j = 0; j < syn.length; ++j){
			idx = listIngredients.findIndex(ele => ele.id == syn[j].ingredient.id);
			ind = listIngredients[idx];
			
			stock = ind.stock;
			
			quanInd = quantity * syn[j].quantity;
			if (quanInd > stock){
				getListIngredient()
				return false;
			}
			stock -= quanInd;
			
			ind.stock = stock;
		}
	}
	
	
	idxSize = dataAjax.productSizes.findIndex(ele => ele.id == sizeId);
	productSize = dataAjax.productSizes[idxSize];
	syns = productSize.syntaxs;
	for (i=0; i < syns.length; ++i){
		idx = listIngredients.findIndex(ele => ele.id == syns[i].ingredient.id);
		ind = listIngredients[idx];
		stock = ind.stock;
		if (quantityNew * syns[i].quantity > stock){
			getListIngredient()
			return false;
		}
		stock -= (quantityNew * syns[i].quantity);
		ind.stock = stock;
	}
	
	getListIngredient();
	return true;
}


const getIndexOrderById = (id) => {
	return listOder.findIndex(ele => ele.id == id);
}

const getIndexIngredientById = (listIngredient, id) => {
	return listIngredient.findIndex(ele => ele.id == id);
}

const renderListOrder = (listOder) => {
	content = listOder.reduce((tmp, ele) => {
	    return tmp + `
	        <tr>
	            <td>${ele.id}</td>
	            <td>${ele.name}</td>
	            <td>${ele.size}</td>
	            <td>
	                <input id="qauntity-${ele.id}" oninput="checkQuantity('${ele.id}')" min="0" type="number" class="input-quantity" value="${ele.quantity}" />
	            </td>
	            <td>${ele.price}</td>
	            <td><button onclick="deleteOrderById('${ele.id}')">Delete</button></td>
	        </tr>
	        `;
	}, '')
	$("#content-order").html(content);
}

function save(){
	if (listOder.length <= 0){
		$('#listEmpty').modal('show');
	} else {
		totalQuantity = listOder.reduce((sum, ele) => sum + ele.quantity, 0);
		if (totalQuantity <= 0){
			$('#listEmpty').modal('show');
		} else {
			$('#dialogOrder').modal('show');
		}
	}
}

function comfirmOder(){
	$('#dialogOrder').modal('hide');
	totalPrice = listOder.reduce((temp, ele) => {
		return temp + (ele.quantity * ele.price);
	}, 0);
	
	csrf = $("input[name='_csrf']").val();
	$.ajax({
	    url: "/orders/api/add",
	    type: "POST",
	    cache: false,
	    contentType: "application/json",
	    data: JSON.stringify({'totalPrice': totalPrice, 'status': 'Ordered'}),
	    success: function(result) {
			dataOrderDetails = [];
			listOder.forEach(ele => {
				orderDetail = {'id': {'productSizeId': ele.id, 'orderId': result}, 'quntity': ele.quantity};
				dataOrderDetails.push(orderDetail)
			})
			$('#orderSuccess').modal('show');
			clearOrder();
			$.ajax({
			    url: "/orders/api/orderdetails",
			    type: "POST",
			    dataType: "json",
			    contentType: "application/json",
			    data: JSON.stringify(dataOrderDetails),
			    success: function(result) {
					
			    },
			    
			    error: function(error) {
			        console.log(error);
			    }
			});
	    },
	    
	    error: function(error) {
	        console.log(error);
	    }
	});
}

function addToOrder(id) {
	$.ajax({
	    url: "http://localhost:8080/api/products/" + id,
	    type: "GET",
	    dataType: "json",
	    success: function(result) {
			dataAjax = result;
	        $("#name-product").text(result.name);
	        $("#des-product").text(result.description);
	        content = result.productSizes.reduce((tmp, ele) => {
				return tmp + `
					<option>${ele.size}</option>
				`;
			}, '');
			$('#size-product').html(content);
			
			contentTableSize = result.productSizes.reduce((tmp, ele) => {
				return tmp + `
				<tr>
					<td>${ele.size}</td>
					<td>${ele.price}</td>
				</tr>`;
			}, '');
			$('#table-size').html(contentTableSize);
			
			size = $('#size-product').val();
			
			idxSize = result.productSizes.findIndex(ele => ele.size == size);
			sizeId = result.productSizes[idxSize].id;
			
			productOrder = { id: sizeId, name: result.name, size: size, quantity: 1, price: 123,  idPro: result.id}
			
			$('#ModalAdd').modal('show')
	    },
	    
	    error: function(error) {
			console.log("error");
	        console.log(error);
	    }
	});
}

function deleteOrderById(id){
	listOder = listOder.filter(ele => ele.id != id)
	renderListOrder(listOder);
	$('#totalPrice').text(calTotalPrice());
}

function calTotalPrice(){
	return listOder.reduce((temp, ele) => {
		return temp + (ele.quantity * ele.price);
	}, 0);
}

function clearOrder(){
	listOder = [];
	productOrder = null;
	renderListOrder(listOder);
}

function checkQuantity(id){
	idx1 = getIndexOrderById(id);
	inputId = '#qauntity-'+id;
	quantityChange = $(inputId).val()*1;
	
	listIngredients = [...ingredients]
	for (i = 0; i < listOder.length; ++i){
		syn = listOder[i].syntax;
		quantity = idx1 != i ? quantity = listOder[i].quantity : quantityChange;
		
		for(j = 0; j < syn.length; ++j){
			idx = listIngredients.findIndex(ele => ele.id == syn[j].ingredient.id);
			ind = listIngredients[idx];
			
			stock = ind.stock;
			
			quanInd = quantity * syn[j].quantity;
			if (quanInd > stock){
				$('#dialogError').modal('show')
				quantityChange = $(inputId).val(listOder[idx1].quantity);
				getListIngredient();
				return;
			}
			stock -= quanInd;
			
			ind.stock = stock;
		}
	}
	
	listOder[idx1].quantity = quantityChange;
	$('#totalPrice').text(calTotalPrice());
	getListIngredient()
}