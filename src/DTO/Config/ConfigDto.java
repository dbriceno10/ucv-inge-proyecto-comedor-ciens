package DTO.Config;

public class ConfigDto {
  private Double valueCF;
  private Double teacherPercentage;
  private Double studentPercentage;
  private Double workerPercentage;
  private String updateAt;

  public ConfigDto() {
  }

  public ConfigDto(Double valueCF, Double teacherPercentage, Double studentPercentage, Double workerPercentage,
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

  public Double getTeacherPercentage() {
    return teacherPercentage;
  }

  public Double getStudentPercentage() {
    return studentPercentage;
  }

  public Double getWorkerPercentage() {
    return workerPercentage;
  }

  public String getUpdateAt() {
    return updateAt;
  }

}
