/** PURE_IMPORTS_START  PURE_IMPORTS_END */
var __extends = (this && this.__extends) || /*@__PURE__*/ (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b)
            if (b.hasOwnProperty(p))
                d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/**
 * An error thrown when duetime elapses.
 *
 * @see {@link timeout}
 *
 * @class TimeoutError
 */
var TimeoutError = /*@__PURE__*/ (/*@__PURE__*/ function (_super) {
    __extends(TimeoutError, _super);
    function TimeoutError() {
        var _this = this;
        var err = _this = _super.call(this, 'Timeout has occurred') || this;
        _this.name = err.name = 'TimeoutError';
        _this.stack = err.stack;
        _this.message = err.message;
        return _this;
    }
    return TimeoutError;
}(Error));
export { TimeoutError };
//# sourceMappingURL=TimeoutError.js.map 
