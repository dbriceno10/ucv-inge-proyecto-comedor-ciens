package Model.Wallet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Model.DTO.Wallet.CreateWalletDto;
import Model.DTO.Wallet.MovementDto;
import Model.DTO.Wallet.UpdateWalletDto;
import Model.DTO.Wallet.WalletDto;
import Utils.Dates;

public class WalletService {
  private String FILE_PATH = "src/Model/Database/Wallet/wallets.json";
  private String MOVEMENTS_FILE_PATH = "src/Model/Database/Wallet/movements.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();

  public WalletService() {
  }

  public WalletService(String walletFilePath, String movementsFilePath) {
    this.FILE_PATH = walletFilePath;
    this.MOVEMENTS_FILE_PATH = movementsFilePath;
  }

  public ArrayList<WalletDto> getAllWallets() {
    // UserSession.getInstance().hasRole(UserRoles.ADMIN); // Verificar que el
    // usuario tenga rol ADMIN
    ArrayList<WalletDto> wallets = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        ArrayList<WalletModel> walletModels = mapper.readValue(file,
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
    // UserSession.getInstance().isAuthenticated();
    WalletModel walletModel = this.getById(id);
    if (walletModel == null) {
      return null;
    }
    return this.mapToDto(walletModel);
  }

  public WalletDto getWalletByUserId(Integer userId) {
    // UserSession.getInstance().isAuthenticated();
    ArrayList<WalletModel> wallets = this.getAll();
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
    // UserSession.getInstance().hasRole(UserRoles.ADMIN); // Verificar que el
    // usuario tenga rol ADMIN
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

  public Boolean delete(Integer id) {
    // UserSession.getInstance().hasRole(UserRoles.ADMIN); // Verificar que el
    // usuario tenga rol ADMIN
    WalletModel existing = getById(id);
    if (existing != null) {
      existing.setDeletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existing);
      return true;
    }
    return false;
  }

  public WalletDto rechargeWallet(Integer userId, Double amount, String reference, String bankName) {
    // UserSession.getInstance().isAuthenticated();
    // Integer userId = //UserSession.getInstance().getUser().getId();
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero.");
    }
    WalletDto wallet = this.getWalletByUserId(userId);
    if (wallet == null) {
      throw new IllegalArgumentException("Wallet not found for user id: " + userId);
    }
    MovementDto movementDto = new MovementDto(
        null,
        wallet.getId(),
        Enums.TypeMovement.ENTRY,
        amount,
        this.datesUtil.getCurrentDateTime(),
        "Recarga de saldo",
        reference,
        bankName);
    return this.addMovement(wallet.getId(), movementDto);
  }

  public WalletDto makePayment(Integer userId, Double amount) {
    // UserSession.getInstance().isAuthenticated();
    // Integer userId = //UserSession.getInstance().getUser().getId();
    WalletDto wallet = this.getWalletByUserId(userId);
    if (wallet == null) {
      throw new IllegalArgumentException("Wallet not found for user id: " + userId);
    }
    MovementDto movementDto = new MovementDto(
        null,
        wallet.getId(),
        Enums.TypeMovement.EXIT,
        amount,
        this.datesUtil.getCurrentDateTime(),
        "Pago de comida",
        null,
        null);
    return this.addMovement(wallet.getId(), movementDto);
  }

  // metodos privados

  private WalletDto addMovement(Integer walletId, MovementDto movementDto) {
    WalletModel existing = getById(walletId);
    if (existing == null) {
      throw new IllegalArgumentException("Wallet not found with id: " + walletId);
    }
    Integer nextId = this.commonServices.getLastIndex(MOVEMENTS_FILE_PATH, MovementModel.class);
    Double newBalance = existing.getBalance();
    if (movementDto.getType().equals(Enums.TypeMovement.ENTRY)) {
      newBalance += movementDto.getAmount();
    } else if (movementDto.getType().equals(Enums.TypeMovement.EXIT)) {
      newBalance -= movementDto.getAmount();
    } else {
      throw new IllegalArgumentException("Invalid movement type: " + movementDto.getType());
    }
    if (newBalance < 0) {
      throw new IllegalArgumentException("Insufficient balance for this movement.");
    }
    existing.setBalance(newBalance);
    WalletModel updatedWallet = this.edit(existing);
    if (updatedWallet == null) {
      return null;
    }
    MovementModel newMovement = new MovementModel(
        nextId,
        walletId,
        movementDto.getType(),
        movementDto.getAmount(),
        movementDto.getDate(),
        movementDto.getDescription(),
        movementDto.getReference(),
        movementDto.getBankName());
    this.saveMovement(newMovement);
    WalletModel wallet = this.getById(walletId);
    if (wallet == null) {
      return null;
    }
    return this.mapToDto(wallet);
  }

  private WalletDto mapToDto(WalletModel walletModel) {
    return new WalletDto(
        walletModel.getId(),
        walletModel.getBalance(),
        walletModel.getUserId(),
        walletModel.getIsActive(),
        walletModel.getCreatedAt(),
        walletModel.getUpdatedAt(),
        this.getMovements(walletModel.getId()));
  }

  private ArrayList<WalletModel> getAll() {
    ArrayList<WalletModel> wallets = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        ArrayList<WalletModel> walletsModels = mapper.readValue(file,
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
    ArrayList<WalletModel> wallets = this.getAll();
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
      ArrayList<WalletModel> wallets = this.commonServices.getAllElements(FILE_PATH, WalletModel.class);
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
      ArrayList<WalletModel> wallets = this.commonServices.getAllElements(FILE_PATH, WalletModel.class);
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

  private MovementDto mapMovementToDto(MovementModel movementModel) {
    return new MovementDto(
        movementModel.getId(),
        movementModel.getWalletId(),
        movementModel.getType(),
        movementModel.getAmount(),
        movementModel.getDate(),
        movementModel.getDescription(),
        movementModel.getReference(),
        movementModel.getBankName());
  }

  private ArrayList<MovementDto> getMovements(Integer walletId) {
    ArrayList<MovementModel> movements = this.commonServices.getAllElements(MOVEMENTS_FILE_PATH, MovementModel.class);
    ArrayList<MovementDto> movementDtos = new ArrayList<>();
    for (MovementModel movement : movements) {
      if (movement.getWalletId().equals(walletId)) {
        movementDtos.add(this.mapMovementToDto(movement));
      }
    }
    return movementDtos;
  }

  private ArrayList<MovementModel> getAllMovements() {
    return this.commonServices.getAllElements(MOVEMENTS_FILE_PATH, MovementModel.class);
  }

  private MovementModel saveMovement(MovementModel movementDto) {
    ArrayList<MovementModel> movements = this.getAllMovements();
    movements.add(movementDto);
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(MOVEMENTS_FILE_PATH);
      mapper.writeValue(file, movements);

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return movementDto;
  }
}
