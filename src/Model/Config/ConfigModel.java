package Model.Config;

public class ConfigModel {
  private Double valueCF;
  private Double teacherPercentage;
  private Double studentPercentage;
  private Double workerPercentage;
  private Double scholarPercentage;
  private String updateAt;

  public ConfigModel() {
  }

  public ConfigModel(Double valueCF, Double teacherPercentage, Double studentPercentage, Double workerPercentage,
      Double scholarPercentage,
      String updateAt) {
    this.valueCF = valueCF;
    this.teacherPercentage = teacherPercentage;
    this.studentPercentage = studentPercentage;
    this.workerPercentage = workerPercentage;
    this.updateAt = updateAt;
    this.scholarPercentage = scholarPercentage;
  }

  // getters
  public Double getValueCF() {
    return valueCF;
  }

  public Double getTeacherPercentage() {
    return teacherPercentage;
  }

  public Double getStudentPercentage() {
    return studentPercentage;
  }

  public Double getWorkerPercentage() {
    return workerPercentage;
  }

  public Double getScholarPercentage() {
    return scholarPercentage;
  }

  public String getUpdateAt() {
    return updateAt;
  }

  // setters
  public void setValueCF(Double valueCF) {
    this.valueCF = valueCF;
  }

  public void setTeacherPercentage(Double teacherPercentage) {
    this.teacherPercentage = teacherPercentage;
  }

  public void setStudentPercentage(Double studentPercentage) {
    this.studentPercentage = studentPercentage;
  }

  public void setWorkerPercentage(Double workerPercentage) {
    this.workerPercentage = workerPercentage;
  }

  public void setUpdateAt(String updateAt) {
    this.updateAt = updateAt;
  }

  public void setScholarPercentage(Double scholarPercentage) {
    this.scholarPercentage = scholarPercentage;
  }

}
