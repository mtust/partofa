package com.partofa.controller.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProtectedPartialController {

    @RequestMapping(value = "/protected/dashboard", method = RequestMethod.GET)
    public String confirmNewPassword() {
        return "/private/dashboard";
    }

    @RequestMapping(value = "/directives/private/main-header", method = RequestMethod.GET)
    public String mainHeader() {
        return "/directives/private/main-header";
    }

    @RequestMapping(value = "/protected/leedsActive", method = RequestMethod.GET)
    public String leedsActive() {
        return "/private/leeds-active";
    }

    @RequestMapping(value = "/protected/leedsContact", method = RequestMethod.GET)
    public String leedsContacts() {
        return "/private/leeds-contact";
    }

    @RequestMapping(value = "/protected/leedsSites", method = RequestMethod.GET)
    public String leedsSites() {
        return "/private/leeds-sites";
    }

    @RequestMapping(value = "/protected/audits", method = RequestMethod.GET)
    public String audits() {
        return "/private/audits";
    }

    @RequestMapping(value = "/protected/quotes", method = RequestMethod.GET)
    public String quotes() {
        return "/private/quotes";
    }

    @RequestMapping(value = "/protected/clients", method = RequestMethod.GET)
    public String clientsActive() { return "/private/clients";  }

    @RequestMapping(value = "/protected/clientsContacts", method = RequestMethod.GET)
    public String clientsContacts() {
        return "/private/clients-contacts";
    }

    @RequestMapping(value = "/protected/clientsSites", method = RequestMethod.GET)
    public String clientsSites() {
        return "/private/clients-sites";
    }

    @RequestMapping(value = "/protected/leeds", method = RequestMethod.GET)
    public String leeds() {
        return "/private/leeds";
    }

    @RequestMapping(value = "/directives/public/pagination", method = RequestMethod.GET)
    public String pagination() {
        return "/directives/private/pagination";
    }

    @RequestMapping(value = "/protected/settings", method = RequestMethod.GET)
    public String settings() {
        return "/private/settings";
    }

    @RequestMapping(value = "/protected/leedsView", method = RequestMethod.GET)
    public String leedsView() {
        return "/private/lead-view";
    }

    @RequestMapping(value = "/protected/clientsView", method = RequestMethod.GET)
    public String clientsView() {
        return "/private/client-view";
    }

    @RequestMapping(value = "/protected/leadSiteView", method = RequestMethod.GET)
    public String leadSiteView() {
        return "/private/lead-site-view";
    }

    @RequestMapping(value = "/protected/clientSiteView", method = RequestMethod.GET)
    public String clientSiteView() {
        return "/private/client-site-view";
    }

    @RequestMapping(value = "/directives/private/add-edit-new-lead", method = RequestMethod.GET)
    public String addEditLead() {
        return "/directives/private/add-edit-new-lead";
    }

    @RequestMapping(value = "/directives/private/add-edit-new-site", method = RequestMethod.GET)
    public String addEditSite() {
        return "/directives/private/add-edit-new-site";
    }

    @RequestMapping(value = "/directives/private/add-edit-new-equipment", method = RequestMethod.GET)
    public String addEditEquipment() {
        return "/directives/private/add-edit-new-equipment";
    }

    @RequestMapping(value = "/directives/private/add-edit-new-measure", method = RequestMethod.GET)
    public String addEditMeasure() {
        return "/directives/private/add-edit-new-measure";
    }

    @RequestMapping(value = "/directives/private/email-template-modal", method = RequestMethod.GET)
    public String emailTemplateModal() {
        return "/directives/private/email-template-modal";
    }

    @RequestMapping(value = "/directives/private/audit-table", method = RequestMethod.GET)
    public String auditTable() {
        return "/directives/private/audit-table";
    }

    @RequestMapping(value = "/directives/private/quote-table", method = RequestMethod.GET)
    public String quoteTable() {
        return "/directives/private/quote-table";
    }

    @RequestMapping(value = "/directives/private/contact-table", method = RequestMethod.GET)
    public String contactTable() {
        return "/directives/private/contact-table";
    }

    @RequestMapping(value = "/protected/users-all", method = RequestMethod.GET)
    public String users() {
        return "/private/users";
    }

    @RequestMapping(value = "/protected/activityView", method = RequestMethod.GET)
    public String activityView() {
        return "/private/activity-view";
    }

    @RequestMapping(value = "/protected/existing-equipment", method = RequestMethod.GET)
    public String existingEquipment() { return "/private/existing-equipment";
    }
    @RequestMapping(value = "/protected/proposed-measures", method = RequestMethod.GET)
    public String proposedMeasures() { return "/private/proposed-measures";
    }
    @RequestMapping(value = "/protected/calculation-assumptions", method = RequestMethod.GET)
    public String calculationAssumptions() { return "/private/calculation-assumptions";
    }
    @RequestMapping(value = "/protected/company-information", method = RequestMethod.GET)
    public String companyInformation() { return "/private/company-information";
    }
    @RequestMapping(value = "/protected/currency-and-taxes", method = RequestMethod.GET)
    public String currencyAndTaxes() { return "/private/currency-and-taxes";
    }
    @RequestMapping(value = "/protected/email-settings-templates", method = RequestMethod.GET)
    public String emailSettingsTemplate() { return "/private/email-settings-templates";
    }



}
