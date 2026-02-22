package DTO.Config;

public class ConfigDto {
  private Double valueCF;
  private Integer teacherPercentage;
  private Integer studentPercentage;
  private Integer workerPercentage;
  private String updateAt;

  public ConfigDto() {
  }

  public ConfigDto(Double valueCF, Integer teacherPercentage, Integer studentPercentage, Integer workerPercentage,
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

}
