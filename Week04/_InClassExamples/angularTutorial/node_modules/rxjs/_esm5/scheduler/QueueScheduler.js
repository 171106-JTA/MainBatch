/** PURE_IMPORTS_START ._AsyncScheduler PURE_IMPORTS_END */
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
import { AsyncScheduler } from './AsyncScheduler';
var QueueScheduler = /*@__PURE__*/ (/*@__PURE__*/ function (_super) {
    __extends(QueueScheduler, _super);
    function QueueScheduler() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return QueueScheduler;
}(AsyncScheduler));
export { QueueScheduler };
//# sourceMappingURL=QueueScheduler.js.map 
