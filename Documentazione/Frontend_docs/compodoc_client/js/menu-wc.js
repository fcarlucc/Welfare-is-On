'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">client documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Properties
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#components-links"' :
                            'data-bs-target="#xs-components-links"' }>
                            <span class="icon ion-md-cog"></span>
                            <span>Components</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="components-links"' : 'id="xs-components-links"' }>
                            <li class="link">
                                <a href="components/AppComponent.html" data-type="entity-link" >AppComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/BookCallComponent.html" data-type="entity-link" >BookCallComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/BookingBoardComponent.html" data-type="entity-link" >BookingBoardComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CardCoachComponent.html" data-type="entity-link" >CardCoachComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CardSectionComponent.html" data-type="entity-link" >CardSectionComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CardServiceComponent.html" data-type="entity-link" >CardServiceComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CoachSectionComponent.html" data-type="entity-link" >CoachSectionComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CreateCoachComponent.html" data-type="entity-link" >CreateCoachComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/CreateServiceComponent.html" data-type="entity-link" >CreateServiceComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DailyAvailabilityComponent.html" data-type="entity-link" >DailyAvailabilityComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DashboardComponent.html" data-type="entity-link" >DashboardComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/EconomicComponent.html" data-type="entity-link" >EconomicComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/FamilyComponent.html" data-type="entity-link" >FamilyComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/FooterComponent.html" data-type="entity-link" >FooterComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ForgotPasswordComponent.html" data-type="entity-link" >ForgotPasswordComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/HeaderAdminComponent.html" data-type="entity-link" >HeaderAdminComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/HeaderCoachComponent.html" data-type="entity-link" >HeaderCoachComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/HeaderComponent.html" data-type="entity-link" >HeaderComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/HomeComponent.html" data-type="entity-link" >HomeComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/MapComponent.html" data-type="entity-link" >MapComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/MeetingBoardComponent.html" data-type="entity-link" >MeetingBoardComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ModifyProfileComponent.html" data-type="entity-link" >ModifyProfileComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/NearMeComponent.html" data-type="entity-link" >NearMeComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/NotFoundComponent.html" data-type="entity-link" >NotFoundComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/OtpVerificationComponent.html" data-type="entity-link" >OtpVerificationComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PhysicalComponent.html" data-type="entity-link" >PhysicalComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PreHeaderComponent.html" data-type="entity-link" >PreHeaderComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ProfileComponent.html" data-type="entity-link" >ProfileComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PsychologicalComponent.html" data-type="entity-link" >PsychologicalComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PurchasedDataComponent.html" data-type="entity-link" >PurchasedDataComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/PurchasedSectionComponent.html" data-type="entity-link" >PurchasedSectionComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ResetPasswordComponent.html" data-type="entity-link" >ResetPasswordComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SectionsComponent.html" data-type="entity-link" >SectionsComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ShowCaseDataComponent.html" data-type="entity-link" >ShowCaseDataComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/ShowCaseSectionComponent.html" data-type="entity-link" >ShowCaseSectionComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SidebarComponent.html" data-type="entity-link" >SidebarComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SidebarComponent-1.html" data-type="entity-link" >SidebarComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SignInComponent.html" data-type="entity-link" >SignInComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SignUpComponent.html" data-type="entity-link" >SignUpComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/SignUpSurveyComponent.html" data-type="entity-link" >SignUpSurveyComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/WelfareCoachComponent.html" data-type="entity-link" >WelfareCoachComponent</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#directives-links"' :
                                'data-bs-target="#xs-directives-links"' }>
                                <span class="icon ion-md-code-working"></span>
                                <span>Directives</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="directives-links"' : 'id="xs-directives-links"' }>
                                <li class="link">
                                    <a href="directives/ErrorMessageApiDirective.html" data-type="entity-link" >ErrorMessageApiDirective</a>
                                </li>
                                <li class="link">
                                    <a href="directives/ErrorMsgFormsDirective.html" data-type="entity-link" >ErrorMsgFormsDirective</a>
                                </li>
                            </ul>
                        </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#injectables-links"' :
                                'data-bs-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/ApiService.html" data-type="entity-link" >ApiService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/AuthService.html" data-type="entity-link" >AuthService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ErrorApiHandlerService.html" data-type="entity-link" >ErrorApiHandlerService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ImageService.html" data-type="entity-link" >ImageService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/MapsService.html" data-type="entity-link" >MapsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/RedirectService.html" data-type="entity-link" >RedirectService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ValidatorService.html" data-type="entity-link" >ValidatorService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interfaces-links"' :
                            'data-bs-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/BookCall.html" data-type="entity-link" >BookCall</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CardCoach.html" data-type="entity-link" >CardCoach</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CardSection.html" data-type="entity-link" >CardSection</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CardService.html" data-type="entity-link" >CardService</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CoachData.html" data-type="entity-link" >CoachData</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CoachSection.html" data-type="entity-link" >CoachSection</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Comment.html" data-type="entity-link" >Comment</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/DailyAvailability.html" data-type="entity-link" >DailyAvailability</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/DaySlot.html" data-type="entity-link" >DaySlot</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FullCard.html" data-type="entity-link" >FullCard</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FullCoach.html" data-type="entity-link" >FullCoach</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Location.html" data-type="entity-link" >Location</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Profile.html" data-type="entity-link" >Profile</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/PurchasedData.html" data-type="entity-link" >PurchasedData</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/PurchasedSection.html" data-type="entity-link" >PurchasedSection</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ShowCaseData.html" data-type="entity-link" >ShowCaseData</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ShowCaseSection.html" data-type="entity-link" >ShowCaseSection</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Survey.html" data-type="entity-link" >Survey</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/TimeSlot.html" data-type="entity-link" >TimeSlot</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/User.html" data-type="entity-link" >User</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#miscellaneous-links"'
                            : 'data-bs-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank" rel="noopener noreferrer">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});