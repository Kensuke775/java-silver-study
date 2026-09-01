module com.service {
    requires com.api;
    exports service;
    provides api.Product
            with service.FoodProduct, service.TechProduct;
}