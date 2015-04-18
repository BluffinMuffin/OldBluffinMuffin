using System.Windows.Forms;

namespace BluffinMuffin.Client.Menu
{
    partial class MenuForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MenuForm));
            this.lblServerAddress = new System.Windows.Forms.Label();
            this.btnTraining = new Button();
            this.btnCareerConnect = new Button();
            this.btnCareerRegister = new Button();
            this.label1 = new System.Windows.Forms.Label();
            this.nudServerPort = new System.Windows.Forms.NumericUpDown();
            this.clstServerName = new System.Windows.Forms.ComboBox();
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).BeginInit();
            this.SuspendLayout();
            // 
            // lblServerAddress
            // 
            this.lblServerAddress.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblServerAddress.Location = new System.Drawing.Point(12, 9);
            this.lblServerAddress.Name = "lblServerAddress";
            this.lblServerAddress.Size = new System.Drawing.Size(353, 19);
            this.lblServerAddress.TabIndex = 14;
            this.lblServerAddress.Text = "Address:";
            this.lblServerAddress.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // btnTraining
            // 
            this.btnTraining.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnTraining.Image = global::BluffinMuffin.Client.Properties.Resources.learn;
            this.btnTraining.Location = new System.Drawing.Point(14, 126);
            this.btnTraining.Name = "btnTraining";
            this.btnTraining.Size = new System.Drawing.Size(353, 65);
            this.btnTraining.TabIndex = 0;
            this.btnTraining.Text = "Start Training";
            this.btnTraining.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnTraining.UseVisualStyleBackColor = false;
            this.btnTraining.Click += new System.EventHandler(this.btnTraining_Click);
            // 
            // btnCareerConnect
            // 
            this.btnCareerConnect.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnCareerConnect.Image = global::BluffinMuffin.Client.Properties.Resources.cards;
            this.btnCareerConnect.Location = new System.Drawing.Point(14, 197);
            this.btnCareerConnect.Name = "btnCareerConnect";
            this.btnCareerConnect.Size = new System.Drawing.Size(353, 65);
            this.btnCareerConnect.TabIndex = 1;
            this.btnCareerConnect.Text = "Continue Career";
            this.btnCareerConnect.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnCareerConnect.UseVisualStyleBackColor = false;
            this.btnCareerConnect.Click += new System.EventHandler(this.btnCareerConnect_Click);
            // 
            // btnCareerRegister
            // 
            this.btnCareerRegister.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnCareerRegister.Image = global::BluffinMuffin.Client.Properties.Resources.add_user;
            this.btnCareerRegister.Location = new System.Drawing.Point(14, 268);
            this.btnCareerRegister.Name = "btnCareerRegister";
            this.btnCareerRegister.Size = new System.Drawing.Size(353, 65);
            this.btnCareerRegister.TabIndex = 2;
            this.btnCareerRegister.Text = "Create Career";
            this.btnCareerRegister.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnCareerRegister.UseVisualStyleBackColor = false;
            this.btnCareerRegister.Click += new System.EventHandler(this.btnCareerRegister_Click);
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 67);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(353, 19);
            this.label1.TabIndex = 16;
            this.label1.Text = "Port:";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // nudServerPort
            // 
            this.nudServerPort.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nudServerPort.Location = new System.Drawing.Point(145, 89);
            this.nudServerPort.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.nudServerPort.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.nudServerPort.Name = "nudServerPort";
            this.nudServerPort.Size = new System.Drawing.Size(84, 23);
            this.nudServerPort.TabIndex = 17;
            this.nudServerPort.Value = new decimal(new int[] {
            4242,
            0,
            0,
            0});
            // 
            // clstServerName
            // 
            this.clstServerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.clstServerName.FormattingEnabled = true;
            this.clstServerName.Items.AddRange(new object[] {
            "127.0.0.1",
            "zeus42.is-a-geek.com"});
            this.clstServerName.Location = new System.Drawing.Point(12, 31);
            this.clstServerName.Name = "clstServerName";
            this.clstServerName.Size = new System.Drawing.Size(353, 24);
            this.clstServerName.TabIndex = 15;
            this.clstServerName.Text = "127.0.0.1";
            // 
            // MenuForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(381, 345);
            this.Controls.Add(this.nudServerPort);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.clstServerName);
            this.Controls.Add(this.lblServerAddress);
            this.Controls.Add(this.btnCareerRegister);
            this.Controls.Add(this.btnCareerConnect);
            this.Controls.Add(this.btnTraining);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "MenuForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Client";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MenuForm_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Button btnTraining;
        private System.Windows.Forms.Label lblServerAddress;
        private Button btnCareerConnect;
        private Button btnCareerRegister;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.NumericUpDown nudServerPort;
        private System.Windows.Forms.ComboBox clstServerName;
    }
}