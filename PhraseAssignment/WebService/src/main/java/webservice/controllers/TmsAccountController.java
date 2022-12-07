package webservice.controllers;


import org.springframework.web.bind.annotation.*;
import webservice.contracts.outputs.TmsAccount;
import webservice.contracts.parameters.TmsAccountParameter;
import webservice.services.TmsAccountService;

@RestController
@RequestMapping("/tmsAccount")
public class TmsAccountController {
    private final TmsAccountService tmsAccountService;

    public TmsAccountController(TmsAccountService tmsAccountService) {
        this.tmsAccountService = tmsAccountService;
    }

    @GetMapping("{id}")
    public TmsAccount getTmsAccount(@PathVariable Long id) {
        return tmsAccountService.get(id);
    }

    @PostMapping
    public TmsAccount createTmsAccount(@RequestBody TmsAccountParameter account) {
        return tmsAccountService.save(account);
    }

    @PutMapping("{id}")
    public TmsAccount editTmsAccount(@PathVariable Long id, @RequestBody TmsAccountParameter account) {
        return tmsAccountService.edit(id, account);
    }

    @DeleteMapping("{id}")
    public void deleteTmsAccount(@PathVariable Long id) {
        tmsAccountService.delete(id);
    }
}
