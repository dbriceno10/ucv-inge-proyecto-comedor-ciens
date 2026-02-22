package Model.Config;

public class ConfigModel {
  private Double valueCF;
  private Integer teacherPercentage;
  private Integer studentPercentage;
  private Integer workerPercentage;
  private String updateAt;

  public ConfigModel() {
  }

  public ConfigModel(Double valueCF, Integer teacherPercentage, Integer studentPercentage, Integer workerPercentage,
      String updateAt) {
    this.valueCF = valueCF;
    this.teacherPercentage = teacherPercentage;
    this.studentPercentage = studentPercentage;
    this.workerPercentage = workerPercentage;
    this.updateAt = updateAt;
  }

  // getters
  public Double getValueCF() {
    return valueCF;
  }

  public Integer getTeacherPercentage() {
    return teacherPercentage;
  }

  public Integer getStudentPercentage() {
    return studentPercentage;
  }

  public Integer getWorkerPercentage() {
    return workerPercentage;
  }

  public String getUpdateAt() {
    return updateAt;
  }

  // setters
  public void setValueCF(Double valueCF) {
    this.valueCF = valueCF;
  }

  public void setTeacherPercentage(Integer teacherPercentage) {
    this.teacherPercentage = teacherPercentage;
  }

  public void setStudentPercentage(Integer studentPercentage) {
    this.studentPercentage = studentPercentage;
  }

  public void setWorkerPercentage(Integer workerPercentage) {
    this.workerPercentage = workerPercentage;
  }

  public void setUpdateAt(String updateAt) {
    this.updateAt = updateAt;
  }

}
