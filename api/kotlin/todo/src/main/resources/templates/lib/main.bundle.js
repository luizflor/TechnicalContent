webpackJsonp(["main"],{

/***/ "../../../../../src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "../../../../../src/app/add/add.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/add/add.component.html":
/***/ (function(module, exports) {

module.exports = "\n<div class=\"row\">\n  <div class=\"col-xs-12\">\n    <form id=\"add\" (ngSubmit)=\"onAdd()\">\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"id\">ID</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"id\"\n                 name=\"id\"\n                 [(ngModel)]=\"todo.id\" disabled\n          >\n        </div>\n      </div>\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"todo\">To do</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"todo\"\n                 name=\"todo\"\n                 [(ngModel)]=\"todo.todo\"\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"isDone\">is done?</label>\n          <input type=\"checkbox\"\n                 class=\"form-control\"\n                 id=\"isDone\"\n                 name=\"isDone\"\n                 [(ngModel)]=\"todo.isDone\"\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-xs-12\">\n          <button class=\"btn btn-success\" type=\"submit\">Add</button>\n        </div>\n      </div>\n    </form>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/add/add.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AddComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_Todo__ = __webpack_require__("../../../../../src/app/model/Todo.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AddComponent = (function () {
    function AddComponent(todoService) {
        this.todoService = todoService;
        this.todo = new __WEBPACK_IMPORTED_MODULE_1__model_Todo__["a" /* Todo */]();
    }
    AddComponent.prototype.ngOnInit = function () {
    };
    AddComponent.prototype.onAdd = function () {
        var _this = this;
        var todo = new __WEBPACK_IMPORTED_MODULE_1__model_Todo__["a" /* Todo */]();
        todo.isDone = this.todo.isDone;
        todo.todo = this.todo.todo;
        this.todoService.addUpdate(todo).subscribe(function (data) {
            _this.todo = data;
        }, function (e) {
            alert(e);
        });
    };
    AddComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-add',
            template: __webpack_require__("../../../../../src/app/add/add.component.html"),
            styles: [__webpack_require__("../../../../../src/app/add/add.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__todo_service__["a" /* TodoService */]])
    ], AddComponent);
    return AddComponent;
}());



/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\n  <div class=\"row\">\n    <div class=\"col-md-2\">\n      <ul class=\"nav\">\n        <li routerLinkActive=\"active\"><a [routerLink]=\"['login']\">Login</a></li>\n        <li routerLinkActive=\"active\"><a [routerLink]=\"['add']\">Add</a></li>\n        <li routerLinkActive=\"active\"><a [routerLink]=\"['delete']\">Delete</a></li>\n        <li routerLinkActive=\"active\"><a [routerLink]=\"['retrieve']\">Retrieve</a></li>\n        <li routerLinkActive=\"active\"><a [routerLink]=\"['update']\">Update</a></li>\n        <li routerLinkActive=\"active\"><a [routerLink]=\"['list']\">List</a></li>\n        <li><a href=\"/swagger-ui.html\">Swagger</a></li>\n\n      </ul>\n    </div>\n    <div class=\"col-md-10\">\n      <router-outlet></router-outlet>\n    </div>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = (function () {
    function AppComponent() {
        this.title = 'td';
    }
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-root',
            template: __webpack_require__("../../../../../src/app/app.component.html"),
            styles: [__webpack_require__("../../../../../src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__list_list_component__ = __webpack_require__("../../../../../src/app/list/list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__add_add_component__ = __webpack_require__("../../../../../src/app/add/add.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__todo_routes__ = __webpack_require__("../../../../../src/app/todo.routes.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__update_update_component__ = __webpack_require__("../../../../../src/app/update/update.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__delete_delete_component__ = __webpack_require__("../../../../../src/app/delete/delete.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__retrieve_retrieve_component__ = __webpack_require__("../../../../../src/app/retrieve/retrieve.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__signup_signup_component__ = __webpack_require__("../../../../../src/app/signup/signup.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__login_loggin_component__ = __webpack_require__("../../../../../src/app/login/loggin.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__token_token_component__ = __webpack_require__("../../../../../src/app/token/token.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__auth_auth_service__ = __webpack_require__("../../../../../src/app/auth/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__auth_AuthGuard__ = __webpack_require__("../../../../../src/app/auth/AuthGuard.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__stomp_ng2_stompjs__ = __webpack_require__("../../../../@stomp/ng2-stompjs/@stomp/ng2-stompjs.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


















var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["I" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_3__app_component__["a" /* AppComponent */],
                __WEBPACK_IMPORTED_MODULE_4__list_list_component__["a" /* ListComponent */],
                __WEBPACK_IMPORTED_MODULE_5__add_add_component__["a" /* AddComponent */],
                __WEBPACK_IMPORTED_MODULE_8__update_update_component__["a" /* UpdateComponent */],
                __WEBPACK_IMPORTED_MODULE_9__delete_delete_component__["a" /* DeleteComponent */],
                __WEBPACK_IMPORTED_MODULE_10__retrieve_retrieve_component__["a" /* RetrieveComponent */],
                __WEBPACK_IMPORTED_MODULE_12__signup_signup_component__["a" /* SignupComponent */],
                __WEBPACK_IMPORTED_MODULE_13__login_loggin_component__["a" /* LogginComponent */],
                __WEBPACK_IMPORTED_MODULE_14__token_token_component__["a" /* TokenComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */], __WEBPACK_IMPORTED_MODULE_7__angular_http__["c" /* HttpModule */], __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */], __WEBPACK_IMPORTED_MODULE_6__todo_routes__["a" /* routing */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_11__todo_service__["a" /* TodoService */], __WEBPACK_IMPORTED_MODULE_15__auth_auth_service__["a" /* AuthService */], __WEBPACK_IMPORTED_MODULE_16__auth_AuthGuard__["a" /* AuthGuard */], __WEBPACK_IMPORTED_MODULE_17__stomp_ng2_stompjs__["a" /* StompService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_3__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "../../../../../src/app/auth/AuthGuard.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthGuard; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__("../../../../../src/app/auth/auth.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AuthGuard = (function () {
    function AuthGuard(auth, router) {
        this.auth = auth;
        this.router = router;
    }
    AuthGuard.prototype.canActivate = function () {
        if (this.auth.isAuthenticated()) {
            return true;
        }
        else {
            this.router.navigate(['login']);
        }
    };
    AuthGuard = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */]])
    ], AuthGuard);
    return AuthGuard;
}());



/***/ }),

/***/ "../../../../../src/app/auth/auth.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jwt_decode__ = __webpack_require__("../../../../jwt-decode/lib/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jwt_decode___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jwt_decode__);

var AuthService = (function () {
    function AuthService() {
    }
    Object.defineProperty(AuthService.prototype, "token", {
        get: function () {
            return this._token;
        },
        enumerable: true,
        configurable: true
    });
    AuthService.prototype.setToken = function (token) {
        this._token = token.token;
        var tokenParse = __WEBPACK_IMPORTED_MODULE_0_jwt_decode__(token.token);
        var userName = tokenParse['sub'];
        var scope = tokenParse['scope'];
        var expirationSeconds = (new Date(tokenParse['exp'] * 1000).getTime() - new Date().getTime()) / 1000;
        console.log('[AuthService]', expirationSeconds, userName, scope);
    };
    AuthService.prototype.logout = function () {
        this._token = '';
    };
    AuthService.prototype.isAuthenticated = function () {
        return this._token
            ? this.isTokenExpired(this._token)
            : false;
    };
    AuthService.prototype.getTokenExpirationDate = function (token) {
        var decoded;
        decoded = __WEBPACK_IMPORTED_MODULE_0_jwt_decode__(token);
        if (!decoded.hasOwnProperty('exp')) {
            return null;
        }
        var date = new Date(0); // The 0 here is the key, which sets the date to the epoch
        date.setUTCSeconds(decoded.exp);
        return date;
    };
    AuthService.prototype.isTokenExpired = function (token, offsetSeconds) {
        var date = this.getTokenExpirationDate(token);
        offsetSeconds = offsetSeconds || 0;
        if (date == null) {
            return false;
        }
        // Token expired?
        return (date.valueOf() > (new Date().valueOf() + (offsetSeconds * 1000)));
    };
    return AuthService;
}());



/***/ }),

/***/ "../../../../../src/app/delete/delete.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/delete/delete.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\n  <div class=\"col-xs-12\">\n    <form id=\"add\" (ngSubmit)=\"onDelete()\">\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"id\">ID</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"id\"\n                 name=\"id\"\n                 [(ngModel)]=\"todo.id\"\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-xs-12\">\n          <button class=\"btn btn-success\" type=\"submit\">Delete</button>\n        </div>\n      </div>\n    </form>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/delete/delete.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DeleteComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_Todo__ = __webpack_require__("../../../../../src/app/model/Todo.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var DeleteComponent = (function () {
    function DeleteComponent(todoService) {
        this.todoService = todoService;
        this.todo = new __WEBPACK_IMPORTED_MODULE_2__model_Todo__["a" /* Todo */]();
    }
    DeleteComponent.prototype.ngOnInit = function () {
    };
    DeleteComponent.prototype.onDelete = function () {
        var _this = this;
        this.todoService.delete(this.todo).subscribe(function (data) {
            _this.todo = data;
        }, function (e) {
            alert(e);
        });
    };
    DeleteComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-delete',
            template: __webpack_require__("../../../../../src/app/delete/delete.component.html"),
            styles: [__webpack_require__("../../../../../src/app/delete/delete.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__todo_service__["a" /* TodoService */]])
    ], DeleteComponent);
    return DeleteComponent;
}());



/***/ }),

/***/ "../../../../../src/app/list/list.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/list/list.component.html":
/***/ (function(module, exports) {

module.exports = "<table class=\"table table-striped table-border table-hover\">\n  <tr>\n    <th>Id</th>\n    <th>Todo</th>\n    <th>Is Done?</th>\n   </tr>\n  <tr *ngFor=\"let t of todos\">\n    <td>{{t.id}}</td>\n    <td>{{t.todo}}</td>\n    <td>{{t.isDone?'true':'false'}}</td>\n  </tr>\n</table>\n"

/***/ }),

/***/ "../../../../../src/app/list/list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__stomp_ng2_stompjs__ = __webpack_require__("../../../../@stomp/ng2-stompjs/@stomp/ng2-stompjs.es5.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ListComponent = (function () {
    function ListComponent(todoService, stomp) {
        this.todoService = todoService;
        this.stomp = stomp;
        //response
        this.response = function (data) {
            console.log(data);
        };
    }
    ListComponent.prototype.ngOnInit = function () {
        this.getTodos();
        this.stompSetup();
    };
    // Stream of messages
    ListComponent.prototype.stompSetup = function () {
        /*
        //configuration
        this.stomp.configure({
          host: 'localhost:3030',
          debug: true,
          queue: {'init': false}
        });
    
        //start connection
        this.stomp.startConnect().then(() => {
          this.stomp.done('init');
          console.log('connected');
    
          //subscribe
          this.subscription = stomp.subscribe('/stomp', this.response);
    
          //send data
          this.stomp.send('destionation', {'data': 'data'});
    
          //unsubscribe
          this.subscription.unsubscribe();
    
          //disconnect
          this.stomp.disconnect().then(() => {
            console.log( 'Connection closed' );
          });
    
        });
        */
    };
    ListComponent.prototype.getTodos = function () {
        var _this = this;
        this.todoService.getTodos().subscribe(function (data) {
            if (data.length === 0) {
                //this.setup();
                return;
            }
            _this.todos = data;
        }, function (e) {
            alert(e);
        });
    };
    ListComponent.prototype.setup = function () {
        var _this = this;
        this.todoService.setup().subscribe(function (data) {
            _this.todos = data;
        }, function (e) {
            alert(e);
        });
    };
    ListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-list',
            template: __webpack_require__("../../../../../src/app/list/list.component.html"),
            styles: [__webpack_require__("../../../../../src/app/list/list.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__todo_service__["a" /* TodoService */], __WEBPACK_IMPORTED_MODULE_2__stomp_ng2_stompjs__["a" /* StompService */]])
    ], ListComponent);
    return ListComponent;
}());



/***/ }),

/***/ "../../../../../src/app/login/loggin.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/login/loggin.component.html":
/***/ (function(module, exports) {

module.exports = "<td-token *ngIf=\"!showLogin()\"></td-token>\n<div class=\"row\" *ngIf=\"showLogin()\">\n  <div class=\"col-xs-12\">\n    <form id=\"add\" (ngSubmit)=\"onLoggin()\">\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"username\">User Name:</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"username\"\n                 name=\"username\"\n                 [(ngModel)]=\"user.username\"\n          >\n        </div>\n      </div>\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"password\">Password:</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"password\"\n                 name=\"password\"\n                 [(ngModel)]=\"user.password\"\n          >\n        </div>\n      </div>\n\n\n      <div class=\"row\">\n        <div class=\"col-xs-12\">\n          <button class=\"btn btn-success\" type=\"submit\">Login</button>\n        </div>\n      </div>\n    </form>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/login/loggin.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LogginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_User__ = __webpack_require__("../../../../../src/app/model/User.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__model_Token__ = __webpack_require__("../../../../../src/app/model/Token.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__auth_auth_service__ = __webpack_require__("../../../../../src/app/auth/auth.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var LogginComponent = (function () {
    function LogginComponent(todoService, authService) {
        this.todoService = todoService;
        this.authService = authService;
        this.user = new __WEBPACK_IMPORTED_MODULE_1__model_User__["a" /* User */]('test', 'password');
    }
    LogginComponent.prototype.ngOnInit = function () {
        // this.user= new User('admin', 'password');
        console.log(this.user);
    };
    LogginComponent.prototype.onLoggin = function () {
        var _this = this;
        this.todoService.loggin(this.user).subscribe(function (data) {
            var token = data.headers.get('authorization');
            _this.authService.setToken(new __WEBPACK_IMPORTED_MODULE_3__model_Token__["a" /* Token */](token));
        }, function (e) {
            //alert(e);
            console.log('[loginComponent]', e);
        });
    };
    LogginComponent.prototype.showLogin = function () {
        return window.location.origin === 'http://localhost:4200' ? false : true;
    };
    LogginComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-loggin',
            template: __webpack_require__("../../../../../src/app/login/loggin.component.html"),
            styles: [__webpack_require__("../../../../../src/app/login/loggin.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__todo_service__["a" /* TodoService */], __WEBPACK_IMPORTED_MODULE_4__auth_auth_service__["a" /* AuthService */]])
    ], LogginComponent);
    return LogginComponent;
}());



/***/ }),

/***/ "../../../../../src/app/model/Todo.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Todo; });
/**
 * Created by luizsilva on 7/9/17.
 */
var Todo = (function () {
    function Todo() {
    }
    return Todo;
}());



/***/ }),

/***/ "../../../../../src/app/model/Token.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Token; });
var Token = (function () {
    function Token(token) {
        this.token = token;
    }
    return Token;
}());



/***/ }),

/***/ "../../../../../src/app/model/User.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return User; });
var User = (function () {
    function User(username, password) {
        this.username = username;
        this.password = password;
    }
    return User;
}());



/***/ }),

/***/ "../../../../../src/app/retrieve/retrieve.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/retrieve/retrieve.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\n  <div class=\"col-xs-12\">\n    <form id=\"add\" (ngSubmit)=\"onRetrieve()\">\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"id\">ID</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"id\"\n                 name=\"id\"\n                 [(ngModel)]=\"todo.id\"\n          >\n        </div>\n      </div>\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"todo\">To do</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"todo\"\n                 name=\"todo\"\n                 [(ngModel)]=\"todo.todo\" disabled\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"isDone\">is done?</label>\n          <input type=\"checkbox\"\n                 class=\"form-control\"\n                 id=\"isDone\"\n                 name=\"isDone\"\n                 [(ngModel)]=\"todo.isDone\" disabled\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-xs-12\">\n          <button class=\"btn btn-success\" type=\"submit\">Retrieve</button>\n        </div>\n      </div>\n    </form>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/retrieve/retrieve.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RetrieveComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_Todo__ = __webpack_require__("../../../../../src/app/model/Todo.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var RetrieveComponent = (function () {
    function RetrieveComponent(todoService) {
        this.todoService = todoService;
        this.todo = new __WEBPACK_IMPORTED_MODULE_2__model_Todo__["a" /* Todo */]();
    }
    RetrieveComponent.prototype.ngOnInit = function () {
    };
    RetrieveComponent.prototype.onRetrieve = function () {
        var _this = this;
        this.todoService.get(this.todo.id.toString()).subscribe(function (data) {
            _this.todo = data;
        }, function (e) {
            alert(e);
        });
    };
    RetrieveComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-retrieve',
            template: __webpack_require__("../../../../../src/app/retrieve/retrieve.component.html"),
            styles: [__webpack_require__("../../../../../src/app/retrieve/retrieve.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__todo_service__["a" /* TodoService */]])
    ], RetrieveComponent);
    return RetrieveComponent;
}());



/***/ }),

/***/ "../../../../../src/app/signup/signup.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/signup/signup.component.html":
/***/ (function(module, exports) {

module.exports = "<p>\n  signup works!\n</p>\n"

/***/ }),

/***/ "../../../../../src/app/signup/signup.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SignupComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SignupComponent = (function () {
    function SignupComponent() {
    }
    SignupComponent.prototype.ngOnInit = function () {
    };
    SignupComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-signup',
            template: __webpack_require__("../../../../../src/app/signup/signup.component.html"),
            styles: [__webpack_require__("../../../../../src/app/signup/signup.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], SignupComponent);
    return SignupComponent;
}());



/***/ }),

/***/ "../../../../../src/app/todo.routes.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return routing; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__list_list_component__ = __webpack_require__("../../../../../src/app/list/list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__add_add_component__ = __webpack_require__("../../../../../src/app/add/add.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__delete_delete_component__ = __webpack_require__("../../../../../src/app/delete/delete.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__update_update_component__ = __webpack_require__("../../../../../src/app/update/update.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__retrieve_retrieve_component__ = __webpack_require__("../../../../../src/app/retrieve/retrieve.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__login_loggin_component__ = __webpack_require__("../../../../../src/app/login/loggin.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__auth_AuthGuard__ = __webpack_require__("../../../../../src/app/auth/AuthGuard.ts");
/**
 * Created by luizsilva on 7/9/17.
 */








var TODO_ROUTES = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'list', component: __WEBPACK_IMPORTED_MODULE_1__list_list_component__["a" /* ListComponent */], canActivate: [__WEBPACK_IMPORTED_MODULE_7__auth_AuthGuard__["a" /* AuthGuard */]] },
    { path: 'add', component: __WEBPACK_IMPORTED_MODULE_2__add_add_component__["a" /* AddComponent */], canActivate: [__WEBPACK_IMPORTED_MODULE_7__auth_AuthGuard__["a" /* AuthGuard */]] },
    { path: 'delete', component: __WEBPACK_IMPORTED_MODULE_3__delete_delete_component__["a" /* DeleteComponent */], canActivate: [__WEBPACK_IMPORTED_MODULE_7__auth_AuthGuard__["a" /* AuthGuard */]] },
    { path: 'update', component: __WEBPACK_IMPORTED_MODULE_4__update_update_component__["a" /* UpdateComponent */], canActivate: [__WEBPACK_IMPORTED_MODULE_7__auth_AuthGuard__["a" /* AuthGuard */]] },
    { path: 'retrieve', component: __WEBPACK_IMPORTED_MODULE_5__retrieve_retrieve_component__["a" /* RetrieveComponent */], canActivate: [__WEBPACK_IMPORTED_MODULE_7__auth_AuthGuard__["a" /* AuthGuard */]] },
    { path: 'login', component: __WEBPACK_IMPORTED_MODULE_6__login_loggin_component__["a" /* LogginComponent */] },
];
var routing = __WEBPACK_IMPORTED_MODULE_0__angular_router__["b" /* RouterModule */].forRoot(TODO_ROUTES);


/***/ }),

/***/ "../../../../../src/app/todo.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TodoService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_Observable__ = __webpack_require__("../../../../rxjs/_esm5/Observable.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__auth_auth_service__ = __webpack_require__("../../../../../src/app/auth/auth.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var TodoService = (function () {
    function TodoService(http, authService) {
        this.http = http;
        this.authService = authService;
        this.url = 'http://localhost:3030';
    }
    // private token: string;
    //
    // setToken(token: Token) {
    //
    //   //  const token = (jwtDecode(this.token.token));
    //   // const userName = (jwtDecode(this.token.token).sub;
    //   // const expirationSeconds = (new Date(token.exp * 1000).getTime() - new Date().getTime()) / 1000;
    //   // console.log(expirationSeconds, userName);
    //
    //   this.token = token.token;
    //   const tokenParse = (jwtDecode(token.token));
    //   const userName = tokenParse['sub'];
    //   const expirationSeconds = (new Date(tokenParse['exp'] * 1000).getTime() - new Date().getTime()) / 1000;
    //   console.log(expirationSeconds, userName);
    //
    //
    // }
    TodoService.prototype.loggin = function (user) {
        var url = this.url + '/login';
        var headers = this.getHeaders();
        var body = JSON.stringify(user);
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Post,
            url: url,
            body: body
        });
        var req = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* Request */](options);
        return this.http.request(req);
        // return this.http.get(url, {  headers: headers })
        //   .map((response: Response) => {
        //     console.log('1',response);
        //     if (response.status === 202) {
        //       throw response;
        //     }
        //     return response;
        //   })
        //   .map((response: Response) => {
        //     console.log('2',response);
        //     return response.text() ? response.json() : '2';
        //   })
        //   .catch(res => {
        //     //return res.status === 202?
        //      return this.http.get(url, {  headers: headers })
        //       //Observable.throw(res);
        //   });
        // .retryWhen(obs => {
        //   console.log('3',obs);
        //   //return Observable.of('a');
        //   return obs; // always just resubscribe without any further logic
        // });
        //return Observable.of('');
        // return this.http.request(req)
        //   .subscribe(data=>{
        //     console.log('[data]',data);
        //     return Observable.of(data)
        //   })
        // .map(resp => {
        // console.log('[login]', resp);
        //  })
        // .catch(res => {
        //   return this.http.request(req)
        //     .map(res=>console.log('[]',res));
        // return res.status === 202?
        //   this.http.get(url):
        //   Observable.throw(res);
        //});
        // .flatMap((response: Response) => {
        //   if (response.status === 200) {
        //     return response //Observable.of('done');
        //   } else if (response.status === 202) {
        //     return this.http.request(req);
        //   }
        // })
    };
    TodoService.prototype.addUpdate = function (todo) {
        var _this = this;
        var url = todo.id
            ? this.url + '/api/todos/' + todo.id
            : this.url + '/api/todos';
        var headers = this.getHeaders();
        var body = JSON.stringify(todo);
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: todo.id ? __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Put : __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Post,
            url: url,
            body: body
        });
        var req = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* Request */](options);
        return this.http.request(req)
            .map(function (resp) { return resp.json(); })
            .catch(function (error) { return _this.handleError(error); });
    };
    TodoService.prototype.delete = function (todo) {
        var _this = this;
        var url = this.url + '/api/todos/' + todo.id;
        var headers = this.getHeaders();
        //const options = new RequestOptions({headers: headers, body: todo});
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Delete,
            url: url
        });
        var req = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* Request */](options);
        return this.http.request(req)
            .map(function (resp) { return resp.json(); })
            .catch(function (error) { return _this.handleError(error); });
    };
    TodoService.prototype.get = function (id) {
        var _this = this;
        var headers = this.getHeaders();
        var url = this.url + '/api/todos/' + id;
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Get,
            url: url
        });
        var req = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* Request */](options);
        return this.http.request(req)
            .map(function (resp) { return resp.json(); })
            .catch(function (error) { return _this.handleError(error); });
    };
    TodoService.prototype.getTodos = function () {
        var _this = this;
        var url = this.url + '/api/todos';
        var headers = this.getHeaders();
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Get,
            url: url
        });
        var req = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* Request */](options);
        return this.http.request(req)
            .map(function (resp) { return resp.json(); })
            .catch(function (error) { return _this.handleError(error); });
    };
    TodoService.prototype.setup = function () {
        var _this = this;
        var url = this.url + '/api/setup';
        var headers = this.getHeaders();
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Get,
            url: url
        });
        var req = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* Request */](options);
        return this.http.request(req)
            .map(function (resp) { return resp.json(); })
            .catch(function (error) { return _this.handleError(error); });
    };
    TodoService.prototype.handleError = function (error) {
        console.log('[Error]', error);
        console.log(JSON.stringify(error));
        return __WEBPACK_IMPORTED_MODULE_3_rxjs_Observable__["a" /* Observable */].throw(error);
    };
    TodoService.prototype.getRequestOptions = function (url, method, body) {
        var headers = this.getHeaders();
        var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* RequestOptions */]({
            headers: headers,
            method: __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestMethod */].Post,
            url: url,
            body: body
        });
        return options;
    };
    TodoService.prototype.getHeaders = function () {
        //const headers = new Headers({'Content-Type': 'application/json', 'Authorization' : this.token, 'Access-Control-Allow-Origin' : '*' });
        var headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Headers */]({ 'Content-Type': 'application/json', 'Authorization': this.authService.token });
        return headers;
    };
    TodoService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */], __WEBPACK_IMPORTED_MODULE_4__auth_auth_service__["a" /* AuthService */]])
    ], TodoService);
    return TodoService;
}());



/***/ }),

/***/ "../../../../../src/app/token/token.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/token/token.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\n  <div class=\"col-xs-12\">\n    <form id=\"add\" (ngSubmit)=\"onSetToken()\">\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"token\">Token:</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"token\"\n                 name=\"token\"\n                 [(ngModel)]=\"token.token\"\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-xs-12\">\n          <button class=\"btn btn-success\" type=\"submit\">Update Token</button>\n        </div>\n      </div>\n    </form>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/token/token.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TokenComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_Token__ = __webpack_require__("../../../../../src/app/model/Token.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_auth_service__ = __webpack_require__("../../../../../src/app/auth/auth.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var TokenComponent = (function () {
    function TokenComponent(authService) {
        this.authService = authService;
        this.token = new __WEBPACK_IMPORTED_MODULE_1__model_Token__["a" /* Token */]('');
    }
    TokenComponent.prototype.ngOnInit = function () {
    };
    TokenComponent.prototype.onSetToken = function () {
        //  const token = (jwtDecode(this.token.token));
        // const userName = (jwtDecode(this.token.token).sub;
        // const expirationSeconds = (new Date(token.exp * 1000).getTime() - new Date().getTime()) / 1000;
        // console.log(expirationSeconds, userName);
        this.authService.setToken(this.token);
    };
    TokenComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-token',
            template: __webpack_require__("../../../../../src/app/token/token.component.html"),
            styles: [__webpack_require__("../../../../../src/app/token/token.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__auth_auth_service__["a" /* AuthService */]])
    ], TokenComponent);
    return TokenComponent;
}());



/***/ }),

/***/ "../../../../../src/app/update/update.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/update/update.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\n  <div class=\"col-xs-12\">\n    <form id=\"add\" (ngSubmit)=\"onUpdate()\">\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"id\">ID</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"id\"\n                 name=\"id\"\n                 [(ngModel)]=\"todo.id\"\n          >\n        </div>\n      </div>\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"todo\">To do</label>\n          <input type=\"text\"\n                 class=\"form-control\"\n                 id=\"todo\"\n                 name=\"todo\"\n                 [(ngModel)]=\"todo.todo\"\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-sm-8 form-group\">\n          <label for=\"isDone\">is done?</label>\n          <input type=\"checkbox\"\n                 class=\"form-control\"\n                 id=\"isDone\"\n                 name=\"isDone\"\n                 [(ngModel)]=\"todo.isDone\"\n          >\n        </div>\n      </div>\n\n      <div class=\"row\">\n        <div class=\"col-xs-12\">\n          <button class=\"btn btn-success\" type=\"submit\">Update</button>\n        </div>\n      </div>\n    </form>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/update/update.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UpdateComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__todo_service__ = __webpack_require__("../../../../../src/app/todo.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_Todo__ = __webpack_require__("../../../../../src/app/model/Todo.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var UpdateComponent = (function () {
    function UpdateComponent(todoService) {
        this.todoService = todoService;
        this.todo = new __WEBPACK_IMPORTED_MODULE_2__model_Todo__["a" /* Todo */]();
    }
    UpdateComponent.prototype.ngOnInit = function () {
    };
    UpdateComponent.prototype.onUpdate = function () {
        var _this = this;
        this.todoService.addUpdate(this.todo).subscribe(function (data) {
            _this.todo = data;
        }, function (e) {
            alert(e);
        });
    };
    UpdateComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'td-update',
            template: __webpack_require__("../../../../../src/app/update/update.component.html"),
            styles: [__webpack_require__("../../../../../src/app/update/update.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__todo_service__["a" /* TodoService */]])
    ], UpdateComponent);
    return UpdateComponent;
}());



/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_13" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map