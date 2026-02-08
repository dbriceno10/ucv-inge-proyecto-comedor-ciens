package Model.Wallet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Utils.Dates;

import DTO.Wallet.CreateWalletDto;
import DTO.Wallet.UpdateWalletDto;
import DTO.Wallet.WalletDto;

public class WalletService {
  private static final String FILE_PATH = "src/Database/Wallet/wallets.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();

  public List<WalletDto> getAllWallets() {
    List<WalletDto> wallets = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<WalletModel> walletModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, WalletModel.class));
        for (WalletModel walletModel : walletModels) {
          if (walletModel.getDeletedAt() == null) { // Filtrar wallets con deletedAt como null
            wallets.add(this.mapToDto(walletModel));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return wallets;
  }

  public WalletDto getWalletById(Integer id) {
    WalletModel walletModel = this.getById(id);
    if (walletModel == null) {
      return null;
    }
    return this.mapToDto(walletModel);
  }

  public WalletDto getWalletByUserId(Integer userId) {
    List<WalletModel> wallets = this.getAll();
    WalletModel found = null;
    for (WalletModel wallet : wallets) {
      if (wallet.getUserId().equals(userId)) {
        found = wallet;
        break;
      }
    }
    if (found == null) {
      return null;
    }
    return this.mapToDto(found);
  }

  public WalletDto create(CreateWalletDto walletDto) {
    Integer nextId = this.commonServices.getLastIndex(FILE_PATH, WalletModel.class);
    String date = this.datesUtil.getCurrentDateTime();
    WalletModel newWallet = new WalletModel(
        nextId, walletDto.getBalance(), walletDto.getUserId(), true, date, date, null);
    WalletModel wallet = this.save(newWallet);
    if (wallet == null) {
      return null;
    }
    return this.mapToDto(wallet);
  }

  public WalletDto update(UpdateWalletDto walletDto) {
    WalletModel existing = getById(walletDto.getId());
    if (existing == null) {
      throw new IllegalArgumentException("Wallet not found with id: " + walletDto.getId());
    }
    String date = this.datesUtil.getCurrentDateTime();
    WalletModel updatedWallet = new WalletModel(
        existing.getId(),
        walletDto.getBalance(),
        existing.getUserId(),
        walletDto.getIsActive(),
        existing.getCreatedAt(),
        date,
        null);
    WalletModel wallet = this.edit(updatedWallet);
    if (wallet == null) {
      return null;
    }
    return this.mapToDto(wallet);
  }

  // metodos privados
  private WalletDto mapToDto(WalletModel walletModel) {
    return new WalletDto(
        walletModel.getId(),
        walletModel.getBalance(),
        walletModel.getUserId(),
        walletModel.getIsActive(),
        walletModel.getCreatedAt(),
        walletModel.getUpdatedAt());
  }

  private List<WalletModel> getAll() {
    List<WalletModel> wallets = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<WalletModel> walletsModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, WalletModel.class));
        for (WalletModel walletModel : walletsModels) {
          if (walletModel.getDeletedAt() == null) { // Filtrar wallets con deletedAt como null
            wallets.add(walletModel);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return wallets;
  }

  private WalletModel getById(Integer id) {
    List<WalletModel> wallets = this.getAll();
    WalletModel found = null;
    for (WalletModel wallet : wallets) {
      if (wallet.getId().equals(id)) {
        found = wallet;
        break;
      }
    }
    return found;
  }

  private WalletModel save(WalletModel wallet) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      List<WalletModel> wallets = this.commonServices.getAllElements(FILE_PATH, WalletModel.class);
      if (wallets == null) {
        wallets = new ArrayList<>();
      }
      wallets.add(wallet);
      mapper.writeValue(file, wallets);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return wallet;
  }

  private WalletModel edit(WalletModel wallet) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      List<WalletModel> wallets = this.commonServices.getAllElements(FILE_PATH, WalletModel.class);
      // Find and update the ingredient
      for (Integer i = 0; i < wallets.size(); i++) {
        if (wallets.get(i).getId().equals(wallet.getId())) {
          wallets.set(i, wallet);
          break;
        }
      }
      mapper.writeValue(file, wallets);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return wallet;
  }
}
