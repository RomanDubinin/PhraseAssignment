package webservice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webservice.contracts.outputs.TmsAccount;
import webservice.contracts.parameters.TmsAccountParameter;
import webservice.services.TmsAccountService;

@RestController
@RequestMapping("/tmsAccount")
public class TmsAccountController {
    @Autowired
    private TmsAccountService tmsAccountService;

    @GetMapping(value = "/get")
    public TmsAccount getTmsAccount(@RequestParam Long id) {
        return tmsAccountService.get(id);
    }

    @PostMapping(value = "/create")
    public TmsAccount createTmsAccount(@RequestBody TmsAccountParameter account) {
        return tmsAccountService.save(account);
    }

    @PutMapping(value = "/edit")
    public TmsAccount editTmsAccount(@RequestParam Long id, @RequestBody TmsAccountParameter account) {
        return tmsAccountService.edit(id, account);
    }

    @DeleteMapping(value = "/delete")
    public void deleteTmsAccount(@RequestParam Long id) {
        tmsAccountService.delete(id);
    }
}
