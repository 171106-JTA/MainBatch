import {AfterViewInit, Directive, ElementRef, Input, Renderer} from '@angular/core';

@Directive ({
    selector: '[appObjectUrl]'
})

/**
 * directive should be used on a container element like a div
 */
export class ObjectDelayDirective implements AfterViewInit {

    @Input()
    public appObjectUrl: string;

    constructor(private el: ElementRef) {
    }

    ngAfterViewInit(): void {
        let objectElement = document.createElement("object");
        console.log(this.appObjectUrl);
        objectElement.setAttribute("data", this.appObjectUrl);
        this.el.nativeElement.append(objectElement);
    }
}
